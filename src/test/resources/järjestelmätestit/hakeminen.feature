# language:fi
Ominaisuus: Käyttäjä voi hakea lukuvinkkejä eri kenttien perusteella

    Tapaus: Käyttäjä voi hakea kaikki lukemattomat lukuvinkit
        Oletetaan on lisätty vinkki otsikolla "Clean Code" ja kirjoittajalla "Robert Martin" ja sen on "luettu"
        Oletetaan on lisätty vinkki otsikolla "Consistency models" ja kirjoittajalla "Nicloa Apicella" ja sen on "lukematta"
        Kun käyttäjä hakee kaikki lukemattomat vinkit
        Niin tulostetaan vain vinkki jonka on kirjoittanut "Robert Martin"

    Tapaus: Käyttäjä voi hakea vinkkejä kirjoittajan nimen perusteella
        Oletetaan on lisätty vinkki otsikolla "Clean Code" ja kirjoittajalla "Robert Martin" ja sen on "luettu"
        Oletetaan on lisätty vinkki otsikolla "Consistency models" ja kirjoittajalla "Nicloa Apicella" ja sen on "lukematta"
        Kun käyttäjä on hakenut kaikki vinkit joilla on kirjoittaja "Martin"
        Niin tulostetaan vain vinkki jonka on kirjoittanut "Robert Martin"

    Tapaus: Käyttäjä voi hakea vinkkejä otsikon perusteella.
        Oletetaan on lisätty vinkki otsikolla "Clean Code" ja kirjoittajalla "Robert Martin" ja sen on "luettu"
        Oletetaan on lisätty vinkki otsikolla "Consistency models" ja kirjoittajalla "Nicloa Apicella" ja sen on "lukematta"
        Kun käyttäjä on hakenut kaikki vinkit otsikolla "Clean Code"
        Niin tulostetaan vain vinkki jonka otsikko on "Clean Code"