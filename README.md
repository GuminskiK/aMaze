# aMaze
## Do czego służy?

Nasz program, służy do znalezienia rozwiązania labiryntu, czyli drogi od wejścia
do labiryntu, aż do wyjścia z niego.

## Jak uruchomić?

Są dwa sposoby na uruchomienie aplikacji aMaze:

1. Klikamy dwukrotnie na plik aMaze.jar
2. W terminalu wpisujemy
```
java -jar aMaze.jar
```

## Jaki format plików jest przyjmowany?

#### a) Plik tekstowy:
- Labirynt jest przedstawiony w postaci, gdzie:
   - P - punkt wejścia do labiryntu
   - K - punkt wyjścia z labiryntu
   - X - ściana
   - spacja - ścieżka  
- Labirynt jest prostokątem

#### b) Plik binarny:
- Zawiera nagłówek pliku:
  - FILE Id w 32 bitach
  - Escape w 8 bitach
  - Liczbę kolumn labiryntu w 16 bitach
  - Liczbę wierszy labiryntu w 16 bitach
  - Współrzędne wejścia do labiryntu (X w 16 bitach i Y w 16 bitach)
  - Współrzędne wyjścia z labiryntu (X w 16 bitach i Y w 16 bitach)
  - 96 bitów zarezerwowanych do przyszłego wykorzystania
  - Liczbę słów kodowych w 32 bitach
  - Offset w pliku do sekcji zawierającej rozwiązanie (nie używamy, ale jest) w 32 bitach
  - Separator w 8 bitach (oznacza początek słowa kodowego)
  - Ściana w 8 bitach (słowo definiujące ścianę labiryntu)
  - Path w 8 bitach (słowo definiujące pole, po którym można się poruszać)
- Słowa kodowe będace odzwierciedleniem jak wygląda labirynt:
  - Separator - 8 bitów
  - Wartość słowa kodowego - 8 bitów
  - Liczba wystąpień (gdzie 0 oznacza jedno wystąpienie)- 8 bitów
- Sekcja nagłówkowa rozwiązania:
  - Identyfikator sekcji rozwiązania - 32 bity
  - Liczba kroków do przejścia (gdzie 0 oznacza jedno wystąpienie) - 8 bitów
- Krok rozwiązania:
  - Kierunek, w którym należy się poruszać - 8 bitów
  - Liczba pól do przejścia w tym kierunku (gdzie 0 oznacza jedno wystąpienie) - 8 bitów

## Jak używać aplikacji?

### Za pomocą termianala:
Po uruchomieniu programu zostaniemy przywitani i poproszeni o podanie ścieżki do pliku z labiryntem:
```
Welcome to the aMaze app, where you can solve your maze!
Please give me a filePath to your maze that you would like me to solve.
```
Należy podać ścieżkę.
Następnie jeżeli brakuje Startu lub Endu labiryntu, to należy podać koordynaty, w których mają się one znaleźć.
```
Please type in coordinates for a new Start:
```
lub 
```
Please type in coordinates for a new End:
```
Podajemy je poprzez wpisanie liczby zatwierdzenie jej za pomocą enter, a następnie wpisanie drugiej liczby i także zatwierdzenie jej za pomocą enter.
Dopóki nie podamy poprawnych koordynatów (należą do ścieżki labiryntu) to aplikacja będzie ponawiała to pytanie.

Następnie labirynt zostanie rozwiązany, wypisane zostanie rozwiązanie i zapytani zostaniemy czy chcemy wyeksportować rozwiązanie (jeżeli jest mniejsze niż 255 kroków):
```
Do you want to export solution? If yes type "yes".
```
Jeżeli chcemy wpisujemy "yes" i zatwierdzamy enterem, wtedy ukaże się nam prośba o podanie nazwy pliku wyjściowego:
```
Please type in the name for the file.
```
Wpisujemy nazwę i plik jest eksportowany. Program prosi nas o kolejny labirynt do rozwiązania.

### Za pomocą GUI.
Po otworzeniu aplikacji otwiera nam się okno GUI. Należy podążać za instrukcjami, które znajdują się w dolnym pasku. Jeżeli potrzebujemy większej pomocy możemy ją uzyskać poprzez wybranie Help->Help.

Opis:
Po otwarciu GUI poza pomocą dostępne jest jedynie wczytywanie plików File->Load Maze. Po wczytaniu musimy przeanalizowac labirynt "Analyze maze". Jeżeli niewybrany został Start/End uruchomi sie nam automatycznie panel Choose Start/End i będzie oczekiwał, że wybierzemy nowy Start lub/i End. Możemy tego dokonać za pomocą Pick Start lub Pick End, które po wybraniu umożliwiają nam wybranie poprzez kliknięcie na ścieżkę labiryntu nowego Start/End. Możemy też tego dokonać poprzez wybranie Type Start lub Type End i wpisanie koordynatów nowego Start/End i zatwierdzenie ich za pomocą przycisku Done. Jeżeli nasz labirynt ma Start End, ale chcemy je zmienić to możemy też ręcznie wybrać opcję Choose Start/End. Jeżeli podczas wybierania nie wybierzemy nowego Start/End, a był on wcześniej wybrany to podczas rozwiązywania zostanie użyty właśnie ten stary Start lub End. Następnie możemy rozwiązać labirynt za pomocą trybu shortest (najkrótsza ścieżka) lub whole (przejście po całym labiryncie). Następnie możemy albo załadować nowy labirynt albo wyeksportować rozwiązanie za pomocą File -> Export Solution (jeżeli jest mniejsze niż 255 kroków).

### Współpraca terminala i GUI
GUI i terminal są wpełni synchronizowane. Ponadto co zostało zawarte w opisie korzystania z samego terminala, podczas rozwiązywania w GUI w terminalu (jeżeli uruchomimy program z terminala) pojawią się także pytania:
```
Do you want to analyze? If yes type in "yes".
Do you want to solve? If yes type in "yes".
```
Pierwsze z nich pyta nas czy chcemy przeanalizować labirynt, a drugie czy chcemy go rozwiązać. Po odpowiedzeniu na którekolwiek z tych pytań "yes" labirynt automatycznie się rozwiąże do końca.

## Autorzy:
Praca została wykonana przez Krzysztofa Gumińskiego i Macieja Bankiewicza.
