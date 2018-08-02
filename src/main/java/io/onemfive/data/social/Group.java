package io.onemfive.data.social;

import java.util.ArrayList;
import java.util.List;

public class Group extends Party {
    private List<Individual> individuals = new ArrayList<>();

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }
}
