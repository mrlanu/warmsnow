package io.lanu.warmsnow.villagesservice.models;

public class VillageViewDirector {

    private Builder builder;

    public VillageViewDirector(Builder builder) {
        this.builder = builder;
    }

    public void constructVillageView(){
        builder.calculateProducedGoods();
        builder.checkFieldsUpgradable();
        //builder.getScheduledTasks();
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }
}
