package Controller.Request;

import Model.PM;

public class SendRequest extends DirectRequest{
    private PM pm;

    public SendRequest (String... args){
        pm = new PM(args[0],args[1]);
    }

    public PM getPm() {
        return pm;
    }
}
