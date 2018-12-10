# language:fi
    Ominaisuus: ReadingTipField-luokka toimii
        Tapaus: Validi ISBN-10 jossa esiintyy X hyväksytään
            Niin "0-8044-2957-X" on validi ISBN

        Tapaus: Validi ISBN-13 hyväksytään
            Niin "978-046502656-2" on validi ISBN

        Tapaus: Validi ISBN-10 hyväksytään
            Niin "046502656-7" on validi ISBN


        Tapaus: Virheellinen ISBN-13 hylätään
            Niin "978-046602656-2" ei ole validi ISBN

        Tapaus: Virhellinen ISBN-10 hylätään
            Niin "046602656-7" ei ole validi ISBN

        Tapaus: Mieletöntä merkkijonoa ei ole hyväksyttävä ISBN
            Niin "asdf" ei ole validi ISBN

        Tapaus: Tyhjä ISBN hyväksytään
            Niin "" on validi ISBN
