package com.myschool.sautil.exim;

import java.util.HashMap;
import java.util.Map;

import com.myschool.exim.constants.EximPolicy;

public class EximPolicyViewMaker {

    Map<EximPolicy, Integer> eximPolicyDependencyCountMap = new HashMap<EximPolicy, Integer>();

    public static void main(String[] args) {
        new EximPolicyViewMaker().start();
    }

    private void start() {
        EximPolicy[] eximPolicies = EximPolicy.values();
        for (EximPolicy eximPolicy : eximPolicies) {
            if (eximPolicy == EximPolicy.SCHOOLS)
            System.out.println(eximPolicy + ": dependencies=" + getDependencyCount(eximPolicy) + ", rank=" + getRank(eximPolicy));
        }
        //System.out.println(eximPolicyDependencyCountMap);
    }

    private int getRank(EximPolicy eximPolicy) {
        int rank = 0;
        EximPolicy[] dependsOn = eximPolicy.getDependsOn();
        if (dependsOn != null) {
            rank++;
            for (EximPolicy parentEximPolicy : dependsOn) {
                int subRank = getRank(parentEximPolicy);
                if (subRank > rank) {
                    rank = subRank;
                }
            }
        }
        return rank;
    }

    private int getDependencyCount(EximPolicy eximPolicy) {
        int dependencyCount= 0;
        EximPolicy[] dependsOn = eximPolicy.getDependsOn();
        if (dependsOn != null) {
            dependencyCount = dependsOn.length;
        }
        return dependencyCount;
    }

}
