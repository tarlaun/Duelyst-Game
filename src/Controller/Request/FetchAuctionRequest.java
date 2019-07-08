package Controller.Request;

import Model.Account;

public class FetchAuctionRequest extends DirectRequest {
    private Account fetcher;
    private int id;

    public FetchAuctionRequest(String... args) {
        this.fetcher = Account.fromJson(args[1]);
        this.id = Integer.parseInt(args[0]);
    }

    public Account getFetcher() {
        return fetcher;
    }

    public int getId() {
        return id;
    }
}
