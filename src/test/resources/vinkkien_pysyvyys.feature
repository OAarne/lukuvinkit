# language: fi
Ominaisuus: Lisätyt lukuvinkit tallentuvat automaattisesti ja niihin pääsee käsiksi seuraavalla käyttökerralla
    
    Tapaus: Listaa-komento tulostaa lisätyn vinkin ohjelman uudelleenkäynnistämisen jälkeen
        Kun Vinkki otsikolla "Huomio" ja kuvauksella "tärkeä" on lisätty
        Ja Ohjelma on käynnistetty uudelleen
        Ja komento "listaa" syötetään
        Niin tulosteessa esiintyy vinkki otsikolla "Huomio" ja kuvauksella "tärkeä"

    Tapaus: Listaa-komento ei tulosta aiemmalla käyttökerralla poistettua vinkkiä
        Kun Vinkki otsikolla "Epähuomio" ja kuvauksella "hups" on lisätty
        Ja Ohjelma on käynnistetty uudelleen
        Ja Vinkki otsikolla "Epähuomio" ja kuvauksella "hups" on poistettu
        Niin tulosteessa ei esiinny vinkkiä otsikolla "Epähuomio" ja kuvauksella "hups"