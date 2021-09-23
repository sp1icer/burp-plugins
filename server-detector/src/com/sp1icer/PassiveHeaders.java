package com.sp1icer;

import burp.*;

import java.util.List;

public class PassiveHeaders {

    private final IBurpExtenderCallbacks callbacks;
    private final IExtensionHelpers helpers;
    private final ServerScanner scanner;

    public PassiveHeaders (ServerScanner scanner) {
        this.scanner = scanner;
        this.callbacks = this.scanner.getCallbacks();
        this.helpers = this.callbacks.getHelpers();
    }

    public List<IScanIssue> doPassiveScan(IHttpRequestResponse requestResponse) {
        List<String> headers = this.helpers.analyzeRequest(requestResponse.getResponse()).getHeaders();

        
    }
}
