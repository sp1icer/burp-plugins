package burp;

import com.sp1icer.PassiveHeaders;

import java.net.URL;
import java.util.List;

public class ServerScanner implements IScannerCheck {

    private final IBurpExtenderCallbacks callbacks;
    private final IExtensionHelpers helpers;

    public ServerScanner(BurpExtender extension) {
        this.callbacks = extension.getCallbacks();
        this.helpers = this.callbacks.getHelpers();
    }

    @Override
    public List<IScanIssue> doPassiveScan(IHttpRequestResponse iHttpRequestResponse) {
        PassiveHeaders passive = new PassiveHeaders(this);
        return passive.doPassiveScan(iHttpRequestResponse);
    }

    @Override
    public List<IScanIssue> doActiveScan(IHttpRequestResponse iHttpRequestResponse, IScannerInsertionPoint iScannerInsertionPoint) {
        return null;
    }

    @Override
    public int consolidateDuplicateIssues(IScanIssue existingIssue, IScanIssue newIssue) {
        boolean names = existingIssue.getIssueName().equals(newIssue.getIssueName());
        boolean urls = existingIssue.getUrl().equals(newIssue.getUrl());

        // If both name and URL are duplicates, return error (-1), else return good (0)
        return (names && urls)? -1: 0;
    }

    public IBurpExtenderCallbacks getCallbacks() {
        return this.callbacks;
    }

    public class ScanIssue implements IScanIssue {

        private final IHttpRequestResponse requestResponse;
        private final String name;
        private final String severity;
        private final String confidence;
        private final String issueBackground;
        private final String issueDetail;
        private final String remediationBackground;
        private final String remediationDetail;
        private final int type;

        public ScanIssue (
                IHttpRequestResponse requestResponse,
                String name,
                String severity,
                String confidence,
                String issueBackground,
                String issueDetail,
                String remediationBackground,
                String remediationDetail) {

            this.requestResponse = requestResponse;
            this.name = name;
            this.severity = severity;
            this.confidence = confidence;
            this.issueBackground = issueBackground;
            this.issueDetail = issueDetail;
            this.remediationBackground = remediationBackground;
            this.remediationDetail = remediationDetail;
            this.type = 0x0800000;
        }

        @Override
        public URL getUrl() {
            return ServerScanner.this.helpers.analyzeRequest(requestResponse).getUrl();
        }

        @Override
        public String getIssueName() {
            return this.name;
        }

        @Override
        public int getIssueType() {
            return this.type;
        }

        @Override
        public String getSeverity() {
            return this.severity;
        }

        @Override
        public String getConfidence() {
            return this.confidence;
        }

        @Override
        public String getIssueBackground() {
            return this.issueBackground;
        }

        @Override
        public String getRemediationBackground() {
            return this.remediationBackground;
        }

        @Override
        public String getIssueDetail() {
            return this.issueDetail;
        }

        @Override
        public String getRemediationDetail() {
            return this.remediationDetail;
        }

        @Override
        public IHttpRequestResponse[] getHttpMessages() {
            IHttpRequestResponse[] messages = { this.requestResponse };
            return messages;
        }

        @Override
        public IHttpService getHttpService() {
            return this.requestResponse.getHttpService();
        }
    }
}
