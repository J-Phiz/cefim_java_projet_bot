package fr.leonie.jp.bot.search;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Search {
    // required
    private final Utilisateur utilisateur;
    private final boolean ville;
    private final int diffAge;

    // optional
    private final boolean sdb;
    private final int nbCommonLoisirs;

    private Search(SearchBuilder builder) {
        utilisateur = builder.utilisateur;
        ville = builder.ville;
        diffAge = builder.diffAge;
        sdb = builder.sdb;
        nbCommonLoisirs = builder.nbCommonLoisirs;
    }

    public ArrayList<Utilisateur> result() {
        ArrayList<Utilisateur> result = Bot.getInstance().getListeUtilisateurs();
        return (ArrayList<Utilisateur>) result.stream()
                .filter(predicateVille()
                        .and(predicateAge())
                        .and(predicateSdb())
                        .and(predicateLoisirs())
                        .and(u -> !u.equals(utilisateur))
                )
                .collect(Collectors.toList());
    }

    private Predicate<Utilisateur> predicateVille() {
        if(ville) {
            return u -> u.getVille().toLowerCase().equals(utilisateur.getVille().toLowerCase());
        } else {
            return u -> true;
        }
    }

    private Predicate<Utilisateur> predicateAge() {
        return u -> Math.abs(u.getAge() - utilisateur.getAge()) <= diffAge;
    }

    private Predicate<Utilisateur> predicateSdb() {
        if(sdb) {
            return u -> !Constant.isNullOrEmpty(u.getSdb()) && u.getSdb().equals(utilisateur.getSdb());
        } else {
            return u -> true;
        }
    }

    private Predicate<Utilisateur> predicateLoisirs() {
        return u -> u.getListeLoisirs().stream().filter(l -> utilisateur.getListeLoisirs().contains(l)).count() >= nbCommonLoisirs;
    }

    // Builder Class
    public static class SearchBuilder {
        // required
        private final Utilisateur utilisateur;
        private final boolean ville;
        private final int diffAge;

        // optional
        private boolean sdb;
        private int nbCommonLoisirs;

        public SearchBuilder(Utilisateur pUtilisateur, boolean pVille, int pDiffAge) {
            utilisateur = pUtilisateur;
            ville = pVille;
            diffAge = pDiffAge;
            sdb = false;
            nbCommonLoisirs = 0;
        }

        public SearchBuilder sdb(boolean pSdb) {
            sdb = pSdb;
            return this;
        }

        public SearchBuilder nbCommonLoisirs(int pNbCommonLoisirs) {
            nbCommonLoisirs = pNbCommonLoisirs;
            return this;
        }

        public Search build() {
            Search search =  new Search(this);
            return search;
        }
    }
}
