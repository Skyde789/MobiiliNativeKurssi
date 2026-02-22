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

# Viikko4
Sovelluksen navigaatiorakenne on toteutettu NavigationBar komponentilla joka sijaitsee sovelluksen alapäädyssä. Käyttäjä voi sieltä lisätä uuden todo:n tai vaihdella kahden eri näkymän välillä. 

MVVM ja navigointi yhdistyvät ongelmitta, kun navigoinnin yhteydessä annetaan jokaiselle ruudulle sama instanssi ViewModelista, joten kaikki ruudut saavat saman datan käyttöönsä.
Molemmat ruudut näyttävät viewModelin sisältä saadun datan ja molemmista ruuduista voi tehdä muokkauksia samaan dataan. 

Kalenteri ruutu ryhmittää todo:t niiden "dueDate" mukaan ja näyttää päivämääriä joiden alle tulee kaikki siihen kuuluvat todo:t. Todo kortteja voi painaa josta aukeaa DetailDialog missä voi muokata / poistaa todo:n. 
Uusien lisääminen tapahtuu alanavigaatiopalkista painamalla "Add" joka aukaisee AddDialog missä voi lisätä uuden todo:n antamalla sille otsikon ja vaihtoehtoisesti kuvauksen. 
### Mitä tarkoittaa navigointi Jetpack Composessa.
Ruutujen välillä siirtymistä Navigation Compose kirjaston avulla. 

Navigointi perustuu reitteihin (routes), joiden avulla määritellään näkymät joihin voi navigoida. 
### Mitä ovat NavHost ja NavController.
NavController vastaa ruutujen vaihdosta. Sen avulla siirrytään ruudusta toiseen esim. kutsumalla navigate("calendar").

NavHost taas määrittelee sovelluksen navigaatiokaavion. Se sisältää kaikki reitit (composablet), joihin sovelluksessa voi navigoida.

# Viikko5

### Mitä Retrofit tekee
Retrofit vastaa HTTP-pyyntöjen hallinnasta Android-sovelluksessa.
Se muodostaa REST-rajapintakutsut (GET) OpenWeatherMap API:in ja palauttaa vastaukset Kotlin-olioina.

### Miten JSON muutetaan dataluokiksi
Retrofit käyttää Gson-kirjastoa, joka muuntaa JSON-vastauksen automaattisesti Kotlinin data class -olioiksi (esim. WeatherResponse).

### Miten coroutines toimii tässä
Sää datan haku tehdään coroutineissa taustasäikeessä, jotta UI ei jäädy.
Kun API-kutsu valmistuu, tulos päivitetään ViewModeliin ja UI reagoi muutokseen automaattisesti.

### Miten UI-tila toimii
WeatherViewModel hallitsee sovelluksen tilaa Result-luokan avulla (Loading, Success, Error).
Jetpack Compose kuuntelee tätä tilaa ja päivittää käyttöliittymän automaattisesti kun tila muuttuu.

### Miten API-key on tallennettu
API-key on tallennettu projektin local.properties tiedostoon mitä ei viedä julkiseksi GitHubiin.

# Viikko6

### Mitä Room tekee tässä projektissa
Room on Androidin tietokantakirjasto, joka tarjoaa turvallisen ja rakenteellisen tavan käyttää SQLite-tietokantaa.

- Entity
Määrittelee tietokannan taulun rakenteen. Yksi Entity vastaa yhtä Todo-tehtävää.

- DAO
Sisältää CRUD-operaatiot (insert, update, delete, query). DAO määrittelee miten dataa käsitellään.

- Database
Room-tietokannan pääluokka, joka yhdistää Entityt ja DAO:t.

- Repository 
Välikappale ViewModelin ja tietokannan välillä. ViewModel kutsuu tietokannasta tiedot tämän avulla. 

- ViewModel
Sisältää UI-logiikan ja antaa datan näkymille.

- UI
Näyttää datan käyttäjälle ja välittää käyttäjän toiminnot ViewModelille.

### Projektin Rakenne
```
com.example.myapplication
│
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   └── TaskDao.kt
│   ├── model
│   │   ├── TaskEntity.kt
│   │   └── TaskUIState.kt
│   └── repository
│       └── TaskRepository.kt
│
├── navigation
│   ├── BottomNavigationBar.kt
│   └── Routes.kt
│
├── view
│   ├── TaskScreen.kt
│   ├── AddDialog.kt
│   ├── DetailDialog.kt
│   └── CalendarScreen.kt
│
├── viewmodel
│   └── TaskViewModel.kt
│
└── MainActivity.kt
```
### Datan kulku (Data Flow)

- Käyttäjä tekee toiminnon UI:ssa (esim. lisää tehtävän)

- UI kutsuu TaskViewModelia

- ViewModel kutsuu TaskRepositorya

- Repository käyttää TaskDaoa

- DAO lukee/kirjoittaa dataa Room-tietokantaan

- Muuttunut data palautuu Flow/State-muodossa takaisin:

- DAO → Repository → ViewModel → UI

- UI päivittyy automaattisesti (Jetpack Compose)
