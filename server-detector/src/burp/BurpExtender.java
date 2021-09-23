package burp;

// Using this tutorial since it's extremely close to
// the end goal: https://www.itsecguy.com/my-first-burp-suite-extension/

public class BurpExtender implements IBurpExtender {

    private IBurpExtenderCallbacks callbacks;
    private final String version;
    private final String name;

    public BurpExtender() {
        this.name = "Server Header Detector";
        this.version = "0.1";
    }

    @Override
    public void registerExtenderCallbacks (IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;

        callbacks.setExtensionName(this.name + " v" + this.version);
        callbacks.registerScannerCheck(new ServerScanner(this));
    }

    public IBurpExtenderCallbacks getCallbacks() {
        return this.callbacks;
    }
}