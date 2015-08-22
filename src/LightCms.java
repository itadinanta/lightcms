import it.itadinanta.lightcms.CmsServer;
import it.itadinanta.lightcms.ServerShutdownHook;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class LightCms {
	static final Logger LOG = Logger.getLogger(LightCms.class);
	public static void main(String[] args) throws Exception {
		// Read the configuration file
		ApplicationContext ctx = new FileSystemXmlApplicationContext("conf/lightcms.xml");
		CmsServer server = (CmsServer) ctx.getBean("cmsServer");
		Runtime.getRuntime().addShutdownHook(new ServerShutdownHook(server));
		server.start();
	}
}