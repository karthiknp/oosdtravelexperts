<%@page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.report.web.viewer.CrystalReportPartsViewer,
com.crystaldecisions.report.web.viewer.CrystalReportViewer,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.crystaldecisions.sdk.occa.report.reportsource.IReportSource"%><%
	// This sample code calls methods from the JRCHelperSample class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the JRCHelperSample class. 

	try {

		String reportName = "PackageList.rpt";
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

			// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
			{
				// Create the CrystalReportViewer object
				CrystalReportViewer crystalReportPageViewer = new CrystalReportViewer();

				//	set the reportsource property of the viewer
				IReportSource reportSource = clientDoc.getReportSource();				
				crystalReportPageViewer.setReportSource(reportSource);

				// set viewer attributes
				crystalReportPageViewer.setOwnPage(true);
				crystalReportPageViewer.setOwnForm(true);

				// Apply the viewer preference attributes



				// Process the report
				crystalReportPageViewer.processHttpRequest(request, response, application, null); 

			}
			// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
			// ****** BEGIN CONNECT CRYSTALREPORTPARTSVIEWER SNIPPET ****************  
			{
				// Create the CrystalReportPartsViewer object
				CrystalReportPartsViewer crystalReportPartsViewer = new CrystalReportPartsViewer();

				//	set the reportsource property of the viewer
				IReportSource reportSource = clientDoc.getReportSource();				
				crystalReportPartsViewer.setReportSource(reportSource);

				// set viewer attributes
				crystalReportPartsViewer.setOwnPage(true);
				crystalReportPartsViewer.setOwnForm(true);
		        
				// Apply the viewer preference attributes



				// Process the report
				crystalReportPartsViewer.processHttpRequest(request, response, application, null); 

			}
			// ****** END CONNECT CRYSTALREPORTPARTSVIEWER SNIPPET ****************		


	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	} 
	
%>