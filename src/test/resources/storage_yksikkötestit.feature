# language: fi
Ominaisuus: Storage-luokka toimii

    Tapaus: Lukuvinkin voi lisätä ja hakea
        Oletetaan että varasto on vasta luotu
        Kun varastoon on lisätty vinkki otsikolla "Kissa"
        Niin varaston palauttamalla tunnisteella haetun vinkin otsikko on "Kissa"

    Tapaus: Lukuvinkin voi lisätä ja poistaa
        Oletetaan että varasto on vasta luotu
        Kun varastoon on lisätty vinkki otsikolla "Kissa"
        Ja varastosta on poistettu vinkki varaston palauttamalla tunnisteella
        Niin varaston palauttamalla tunnisteella ei löydy vinkkiä

    Tapaus:
        Oletetaan että varasto on vasta luotu
        Kun varastoon on lisätty vinkki otsikolla "Kissa"
        Ja varastoon on lisätty vinkki otsikolla "Koira"
        Niin varaston palauttamalla listalla on kaksi vinkkiä


    Tapaus:
        Oletetaan että varasto on vasta luotu
        Kun varastoon on lisätty vinkki otsikolla "Kissa"
        Niin varastosta palautetaan komennolla jsoniksi json-muotoinen string

    Tapaus:
        Oletetaan että varasto on vasta luotu
        Kun varastoon on lisätty vinkki otsikolla "Kissa"
        Ja luodaan varasto jsonmuotoisesta vinkistä "Kissa"
        Niin varastojen sisällöt ovat samat
