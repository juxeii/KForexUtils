package com.jforex.kforexutils.jfclient;

import com.dukascopy.api.*;
import com.jforex.kforexutils.misc.KForexUtils;

@RequiresFullAccess
@Library("D:/data/Projects/repos/KForexTestRunner/build/libs/KForexTestRunner-1.0-SNAPSHOT-all.jar")
public class KForexUtilsStrategy implements IStrategy {
    private IEngine engine;
    private IConsole console;
    private IHistory history;
    private IContext context;
    private IUserInterface userInterface;
    private KForexUtils kForexUtils;
    private JFClientBridge bridge;

    public void onStart(IContext context) {
        this.engine = context.getEngine();
        this.console = context.getConsole();
        this.history = context.getHistory();
        this.context = context;
        this.userInterface = context.getUserInterface();
        this.kForexUtils = new KForexUtils(context);
        this.bridge = new MyJFClientExampleClass();

        bridge.onStart(kForexUtils);
    }

    public void onAccount(IAccount account) {
    }

    public void onMessage(IMessage message) {
        kForexUtils.getMessageGateway().onMessage(message);
    }

    public void onStop() {
        bridge.onStop();
    }

    public void onTick(Instrument instrument,
                       ITick tick) {
    }

    public void onBar(Instrument instrument,
                      Period period,
                      IBar askBar,
                      IBar bidBar) {
    }
}