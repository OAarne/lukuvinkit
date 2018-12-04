# language:fi
Ominaisuus: Käyttäjä voi hakea lukuvinkkejä eri kenttien perusteella

    Tapaus: Käyttäjä voi hakea kaikki lukemattomat lukuvinkit
        Oletetaan on lisätty vinkit parametrein {Otsikko="Clean Code" Kirjoittajat="Robert Martin" Luettu="luettu"} ja {Otsikko="Consistency models" Kirjoittajat="Nicola Apicella" Luettu="lukematta"}
        Kun lukija on tehnyt haun parametreilla {Luettu="lukematta"}
        Niin tulostetaan vain vinkki jonka on kirjoittanut {Robert Martin}

    Tapaus: Käyttäjä voi hakea vinkkejä kirjoittajan nimen perusteella
        Oletetaan on lisätty vinkit parametrein {Otsikko="Clean Code" Kirjoittajat="Robert Martin" Luettu="luettu"} ja {Otsikko="Consistency models" Kirjoittajat="Nicola Apicella" Luettu="lukematta"}
        Kun lukija on tehnyt haun parametreilla {Kirjoittajat="Martin"}
        Niin tulostetaan vain vinkki jonka on kirjoittanut {Robert Martin}

    Tapaus: Käyttäjä voi hakea vinkkejä otsikon perusteella.
        Oletetaan on lisätty vinkit parametrein {Otsikko="Clean Code" Kirjoittajat="Robert Martin" Luettu="luettu"} ja {Otsikko="Consistency models" Kirjoittajat="Nicola Apicella" Luettu="lukematta"}
        Kun lukija on tehnyt haun parametreilla {Otsikko="Clean Code"}
        Niin tulostetaan vain vinkki jonka otsikko on {Clean Code}