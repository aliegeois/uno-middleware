# Rapport projet Middleware - Uno en RMI

- [Charles Bequet--Ermoy](https://github.com/cbequet)
- [Ronan Guéguen](https://github.com/Kairides)
- [Hanane Hadji](https://github.com/nounou95)
- [Arthur Liégeois](https://github.com/aliegeois)

## Règles du jeu
Le Uno possède un grand nombre de variations des règles, nous nous sommes basés sur celles-ci : [Uno rules by Kade "Archer" Hansson](http://play-k.kaserver5.org/Uno.html).

## Structure du programme
Architecture client/serveur complètement centralisée. Les clients se connectent au serveur pour chercher une partie, et conservent cette connexion lorsque la partie est lancée. Ceci est nécessaire puisque le Uno a besoin d’une pioche centrale qui est partagée entre les joueurs. Faire un système décentralisé aurait nécessité de faire un consensus à chaque fois qu’un joueur veut piocher une carte, ce qui est très lourd.

## Architecture
### Client
Le client est un state pattern : chaque état correspond à une attente (de l’utilisateur ou d’un autre client) et chaque transition est une action (effectuée par l’utilisateur ou par un autre client). Par exemple l’état [Lobby](client/src/main/java/fr/univnantes/state/LobbyState.java) indique que le client est en attente d’autres joueurs. Il possède les transitions suivante : [leaveLobby](client/src/main/java/fr/univnantes/state/LobbyState.java#L10), appelé par le client pour quitter le lobby; [setReady(boolean)](client/src/main/java/fr/univnantes/state/LobbyState.java#L15) appelée par l’utilisateur qui permet d’indiquer au serveur que le client est prêt à lancer la partie (ou qu’il n’est plus prêt); et [startGame(List&lt;Player&gt;, List&lt;Card&gt;, Card)](client/src/main/java/fr/univnantes/state/LobbyState.java#L22) qui est appelée par le serveur et qui fait commencer la partie en indiquant la liste des autres joueurs de la partie, la liste des cartes de départ du client et la carte du dessus de la pioche.

### Serveur
Le serveur est très simple, il possède une liste des joueurs connectés et il connaît les règles du jeu pour s’assurer du bon déroulement de la partie. Il n’y a pas de possibilité de lancer plusieurs parties en même temps, si un client se connecte alors qu’une partie est déjà en cours, sa connection sera refusée.

## Classes partagées
Un certain nombre de classes sont partagées entre le client est le serveur : d’abord l’interfaces du client et du serveur puisque chacun doit pouvoir communiquer avec l’autre; ensuite les classes des cartes puisqu’elles ne font que stocker de l’information et nous avons besoin que le client et le serveur possèdent la même représentation de cette information.

## Interface utilisateur

Nous n’avons pas fait d’interface graphique (on a essayé mais c’est compliqué) mais nous avons quand même prévu la possibilité d’en implémenter une grâce à l’interface java [IUserInterface](client/src/main/java/fr/univnantes/IUserInterface.java). Cette interface est actuellement implémentée dans la classe [TextualUserInterface](client/src/main/java/fr/univnantes/TextualUserInterface.java) et permet de gérer toutes les interactions entre l’utilisateur et le programme via la console. Si dans le futur nous souhaitons faire une interface graphique, il suffirait de faire une autre implémentation de IUserInterface et le programme ne nécessiterait aucune modification structurelle pour fonctionner.

## Tester le programme

Nous avons deux projets Maven : un pour le client et l’autre pour le serveur dans les dossiers [client](client) et [server](server) respectivement. Ceci permet de compiler chaque projet avec un simple mvn package. Le .jar est généré dans le dossier target.
