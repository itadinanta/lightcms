<p> Eccomi ancora qui, nella terza incarnazione di Itadinanta. Questa volta ho 
scritto il motore in Java, appoggiandomi a Jetty e Spring. La smetter&ograve; 
mai di reinventare la ruota ogni volta? Beh, in realt&agrave; ci ho provato, 
e non una volta sola, ma non sono mai contento di quello che trovo "gi&agrave; pronto".
</p>

<p>Avevo scritto la prima versione in PHP, appoggiandomi al mio (dagli!) mini-framework 
"elib", che avevo scritto e utilizzato a suo tempo per costruire anche il sito di 
<@nav.link url="http://www.dotsrl.com"/> e <@nav.link url="http://www.radixmalorum.it"/>. 
Soluzione tutt'altro che perfetta, visto che:
</p>

<ul>
	<li>Era un casino trovare collaboratori per lo sviluppo. Pur essendo scritto interamente
	in PHP, richiedeva una conoscenza decisamente profonda del linguaggio e della libreria,
	senza tener conto di tutta la robaccia scritta da me.
	</li>
	<li>Il sito aveva una tonnellata di prerequisiti, 
	tra librerie di sistema, MySQL, configurazione di Apache, 
	estensioni di PHP, e questo lo rendeva difficile da portare 
	da una piattaforma all'altra, anche tra diverse distro Linux.
	</li>
	<li>La metodologia di sviluppo era piuttosto "artigianale": Xemacs e Galeon, 
	sistema di debugging senza debugger, logging remoto "fatto in casa", 
	niente unit testing, niente IDE.</li>
	<li>Varie ed eventuali</li>
</ul>

<p>Comunque, funzionava. Almeno fino a quando, dopo un catastrofico 
crash hardware con tanto di disco irriconoscibile dal BIOS, mi son trovato punto 
e a capo a dover reinstallare tutto. Gi&agrave; solo per essere passato da Woody 
(server morto) a Sarge, (nuova installazione) i siti non funzionavano pi&ugrave;</p>