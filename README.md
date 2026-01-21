# Viikko1

Simppeli Kotlinilla tehty projekti jossa testaillaan hyvin perusjuttuja android kehityksestä.

Tehtävien data on vain lähimuistissa ja resetoituu kun sovellus käynnistetään uudelleen. 

## Ominaisuudet
Voit:

- Listaa tehtäviä

- Merkitä tehtäviä suoritetuksi

- Suodattaa suoritetut tehtävät

- Järjestää tehtävät päivämäärän mukaan

- Lisää uusia tehtäviä

# Viikko2
### Selitä, miten Compose-tilanhallinta toimii
Compose-tilanhallinnalla sovellus voi helposti päivittää vain ne osat käyttöliittymästä, jotka muuttuvat, ilman että muiden osien tarvitsee piirtyä uudelleen.

### Kerro, miksi ViewModel on parempi kuin pelkkä remember
ViewModelin sisällä oleva data säilyy paremmin muistissa verrattuna rememberiin. Esimerkiksi puhelimen kääntyessä vaakatasoon remember-tila nollaantuisi, mutta ViewModelin avulla data pysyy tallessa.
ViewModelin avulla voidaan erottaa UI:n ja logiikka toisistaan, välttäen spagettikoodia.
