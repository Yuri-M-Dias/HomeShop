package br.ufg.inf.homeshop.wrapper;

import java.util.List;

import br.ufg.inf.homeshop.model.Market;

public class SupermarketsWrapper {
    private List<Market> marketList;

    public SupermarketsWrapper(List<Market> marketList) {
        this.marketList = marketList;
    }

    public List<Market> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<Market> marketList) {
        this.marketList = marketList;
    }
}
