package io.lanu.warmsnow.villagesservice.models;

public class VillageViewDirector {

    private Builder builder;

    public VillageViewDirector(Builder builder) {
        this.builder = builder;
    }

    public void constructVillageView(){
        builder.reset();
        builder.calculateProducedGoods();
        builder.checkFieldsUpgradable();
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }
}
