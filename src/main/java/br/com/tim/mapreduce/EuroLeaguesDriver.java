package br.com.tim.mapreduce;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineSequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import br.com.tim.driverutils.DriverUtility;
import br.com.tim.driverutils.DriverUtils;
import br.com.tim.driverutils.Entry;
import br.com.tim.exception.CommonsException;
import br.com.tim.metric.JobMetric;
import br.com.tim.metric.JobMetricHelper;
import br.com.tim.metric.MetricConstants;
import br.com.tim.model.EuroLeaguesKey;
import br.com.tim.model.EuroLeagueslValue;
import br.com.tim.utils.CommonsConstants;
import br.com.tim.utils.EuroLeagueConstants;
import br.com.tim.utils.EuroLeagueUtils;
import br.com.tim.utils.PropertiesConfig;

public class EuroLeaguesDriver extends Configured implements Tool{
	
	private static Logger LOG = Logger.getLogger(EuroLeaguesDriver.class);
	
	private static final String REPORT_OUTPUT_PATH = EuroLeagueConstants.GPDB_INPUT;
	private static final String REPORT_STAGE_PATH = EuroLeagueConstants.REPORT_PROCESSING_PATH;
	
	private int retorno = CommonsConstants.ERROR;
	
	public Entry[] entrys;
	
	private Job job;
	protected JobMetric metrics;
	protected String jobStageDir = "";
	protected Path jobStagePath;
	protected String timestamp;
	
	@Override
	public int run(String[] args) throws Exception {
		try {
			if (!PropertiesConfig.systemPropertiesValidate()) {
				System.err.println("Either System property: " + CommonsConstants.LOG4J_CONFIGURATION + " or "
						+ CommonsConstants.PROJECT_CONFIGURATION + " or " + CommonsConstants.ENVIRONMENT_CONFIGURATION
						+ " is not set.");
				System.exit(CommonsConstants.ERROR);
			}

			PropertyConfigurator.configure(System.getProperty(CommonsConstants.LOG4J_CONFIGURATION));

			Configuration conf = getConf();
			conf.addResource(new Path(System.getProperty(CommonsConstants.ENVIRONMENT_CONFIGURATION)));
			conf.addResource(new Path(System.getProperty(CommonsConstants.PROJECT_CONFIGURATION)));
			conf.addResource(new Path(System.getProperty(MetricConstants.JOB_METRICS_CONFIGURATION)));

			job = Job.getInstance(conf);
			
			// Create metrics file
			String metricsFilePath = System.getProperty(MetricConstants.GENERIC_FILE_METRICS_DEFINITION);
			String customMetrics = System.getProperty(MetricConstants.CUSTOM_FILE_METRICS_DEFINITION);

			// create JobMetric
			if (customMetrics != null)
				metrics = new JobMetric(conf, metricsFilePath, customMetrics);
			else
				metrics = new JobMetric(conf, metricsFilePath);

			configureCommons();
			configure( args );

			if (job.waitForCompletion(true) && job.isSuccessful()) {

				LOG.info("=============================== Job Finished SUCCESSFULY ==================================");
				Thread.sleep(Long.valueOf(conf.get(MetricConstants.MILLIS_TO_WAIT)));
				
				LOG.info("======================== Running post processing steps... =================================");
				postExecutionSteps();
				
				LOG.info("=============================== SAVING JOB METRICS ========================================");
				JobMetricHelper.generateMetrics(this.job, null, metrics);
				retorno = CommonsConstants.SUCCESS;

			} else {
				LOG.info("=============================== SAVING STAGE METRICS ========================================");
				JobMetricHelper.generateMetrics(this.job, null, metrics);

				LOG.info("========================= STAGE FINISHED UNSUCCESSFULY ===================================");
				retorno = CommonsConstants.ERROR;
			}
			
		} catch (Exception e) {
			throw new CommonsException(e.getMessage(), e);
		}
		return retorno;
	}

	protected void configure(String[] args) throws Exception {

        Configuration configuration = this.job.getConfiguration();
        FileSystem fs = FileSystem.get(configuration);
		
		EuroLeagueUtils.configurationFileValidate(configuration);
		LOG.debug("Configuration file validated successfully!");		

		entrys = getEntry(CombineTextInputFormat.class);
		
        DriverUtils.addInputPaths(args, configuration, this.job, 1, entrys);
        
        timestamp = LocalDateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));

		// OUTPUT PATH
        jobStageDir = configuration.get( REPORT_STAGE_PATH );
        jobStagePath = new Path(jobStageDir,timestamp);
		
		if (fs.exists(jobStagePath)) {
			LOG.info("WARN: jobStagePath: " + jobStagePath.toString() + " already exists, deleting it...");
			fs.delete(jobStagePath, true);
		}
		LOG.info("jobStagePath: " + jobStagePath.toString());

		TextOutputFormat.setOutputPath(job, jobStagePath);
		
	}

	protected void configureCommons() throws Exception {

		job.setJobName( job.getConfiguration().get("process-id") );
		job.setJarByClass(getClass());
		job.setMapOutputKeyClass(EuroLeaguesKey.class);
		job.setMapOutputValueClass(EuroLeagueslValue.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setReducerClass(EuroLeaguesReducer.class);
		
	}
	
	@SuppressWarnings("rawtypes")
	protected Entry[] getEntry(Class<? extends InputFormat> inputFormat) {
		
		//Configuration conf = this.job.getConfiguration();
		
        //int defaultMinusDays = conf.getInt("default-processing-date", -1);
		
		Entry bpl = new Entry(EuroLeagueConstants.BPL, EuroLeagueConstants.BPL_INPUT, false, inputFormat,
				BPLMapper.class);
        
		Entry bundesliga = new Entry(EuroLeagueConstants.BUNDESLIGA, EuroLeagueConstants.BUNDESLIGA_INPUT, false, inputFormat,
				BundesligaMapper.class);
		
		Entry laliga = new Entry(EuroLeagueConstants.LA_LIGA, EuroLeagueConstants.LA_LIGA_INPUT, false, inputFormat,
				LaLigaMapper.class);
		
		Entry leagueone = new Entry(EuroLeagueConstants.LEAGUE_ONE, EuroLeagueConstants.LEAGUE_ONE_INPUT, false, inputFormat,
				LeagueOneMapper.class);
		
		Entry serieatim = new Entry(EuroLeagueConstants.SERIE_A_TIM, EuroLeagueConstants.SERIE_A_TIM_INPUT, false, inputFormat,
				SerieATimMapper.class);
		
		return Arrays.asList(bpl, bundesliga, laliga, leagueone, serieatim).toArray(new Entry[]{});
		
	}	
	
	protected void postExecutionSteps() throws Exception { 
    	
    	Configuration configuration = this.job.getConfiguration();
		FileSystem fs = FileSystem.get(configuration);
		
		SimpleDateFormat gpdbDtFrmt = new SimpleDateFormat("yyMMdd");
		
		String datePath = gpdbDtFrmt.format(entrys[0].getLastDateFound(configuration));
		
		String jobOutputDir = configuration.get(REPORT_OUTPUT_PATH);
		
		Path jobOutputPath = new Path(jobOutputDir, timestamp + "_" + datePath + "_" + datePath); 
		
		if ( fs.exists(jobOutputPath) )
			fs.delete(jobOutputPath, true);
    
		DriverUtility.move(fs, jobOutputPath, jobStagePath, "*");
		
		fs.delete(jobStagePath, true);
		
		LOG.info("Output Path: " + jobOutputPath.toUri());
	    	
	}	
	
	public static void main(String[] args) throws Exception {

		LOG.info("====================================== BEGIN EUROPEAN LEAGUES DATE GOLS REPORT JOB ======================================");
		EuroLeaguesDriver driver = new EuroLeaguesDriver();
		int exitCode = ToolRunner.run(driver, args);
		LOG.debug("Exit code: " + exitCode);
		LOG.info("====================================== END EUROPEAN LEAGUES DATE GOLS REPORT JOB ======================================");
		System.exit(exitCode);
		 
	}
	
	
}
