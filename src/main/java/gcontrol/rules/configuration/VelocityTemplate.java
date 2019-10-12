package gcontrol.rules.configuration;

import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.tools.generic.ComparisonDateTool;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * This Class created for Velocity Engine. Velocity Engine we used for Email
 * process. We required some fixed format of email templates.
 * 
 * @author dushyant
 *
 */
public class VelocityTemplate {

	/**
	 * This Class created for Velocity Engine. Velocity Engine we used for Email
	 * process. We required some fixed format of email templates.
	 */
	public VelocityTemplate() {
	}

	/**
	 * This Method we used for Velocity Template. First initialize Velocity
	 * Engine then read template data.
	 * 
	 * @param path
	 *            , Path of template file.
	 * @param templateName,
	 *            Template File Name.
	 * @param object,
	 *            Template Parameter Objects.
	 * @return , Return String Template Data.
	 */
	public String readTemplate(String path, String templateName, Object object) {
		try {

		
			/* first, get and initialize an engine */
			VelocityEngine ve = new VelocityEngine();
		
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path);
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
			ve.init();

			/* add that list to a VelocityContext */
			VelocityContext context = new VelocityContext();
			context.put("object", object);
			context.put("date", new ComparisonDateTool());
			/* get the Template */
			Template t = ve.getTemplate(templateName);
			/* now render the template into a Writer */
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			/* use the output in your email body */

			return writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}