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

# Viikko3
### MVVM ja miksi se on hyödyllinen Compose-sovelluksissa

MVVM on tapa jakaa sovellus selkeisiin osiin: data (Model), käyttöliittymä (View) ja logiikka/tila (ViewModel).

Compose-sovelluksissa MVVM toimii hyvin, koska UI reagoi suoraan tilan muutoksiin. ViewModel hoitaa sovelluksen tilan ja logiikan, ja Compose vain näyttää sen.

### Miten StateFlow toimii

StateFlow on tapa hallita ja kuunnella tilaa. Sillä on aina joku arvo, ja kun arvo muuttuu, UI päivittyy automaattisesti.

ViewModelissa tilaa pidetään yleensä MutableStateFlow:ssa ja UI saa siitä vain luettavan StateFlow-version. Compose käyttää collectAsState()-funktiota, jonka avulla muutokset näkyvät heti käyttöliittymässä.
