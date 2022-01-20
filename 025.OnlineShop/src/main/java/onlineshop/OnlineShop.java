package onlineshop;

import backend.DataAPI;

public class OnlineShop {
    public static void main(String[] args) {
        DataAPI api = new DataAPI();
        api.init();
        api.close();
    }
}
