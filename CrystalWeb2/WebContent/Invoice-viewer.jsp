<%@page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase"%><%
	// This sample code calls methods from the JRCHelperSample class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the JRCHelperSample class. 

	try {

		String reportName = "Invoice.rpt";
		ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);

		if (clientDoc == null) {
			// Report can be opened from the relative location specified in the CRConfig.xml, or the report location
			// tag can be removed to open the reports as Java resources or using an absolute path
			// (absolute path not recommended for Web applications).

			clientDoc = new ReportClientDocument();
			
			// Open report
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

			// ****** BEGIN LOGON DATASOURCE SNIPPET ****************  
			{
				//	Database username and password
				String userName = "ictoosd";			// TODO: Fill in database user
				String password = "ictoosd";		// TODO: FIll in password

				// logon to database
				JRCHelperSample.logonDataSource(clientDoc, userName, password);
			}
			// ****** END LOGON DATASOURCE SNIPPET **************** 		


			// Store the report document in session
			session.setAttribute(reportName, clientDoc);

		}

		

	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	} 
	
%>