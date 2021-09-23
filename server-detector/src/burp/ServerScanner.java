package burp;

import com.sp1icer.PassiveHeaders;
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
}
