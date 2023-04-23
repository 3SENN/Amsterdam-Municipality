-- INSERT INTO QUESTION
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (1,0,'Een jonge man,  in een rolstoel, wil naar de sporthal toe omdat hij daar elke week gaat basketballen. Hij is elke week een uur onderweg, heen en terug. Hij maakt gebruik van WMO vervoer en de til service. Hij gaat van zijn speciaal onderwijs in Nieuw-West naar  zijn sportclub in zit in Noord. Dit is de enige plek met een rolstoel basketbalclub in de stad.',500, 1,'Hoeveel inwoners hebben een WMO pas?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (2,0,'Een jonge man,  in een rolstoel, wil naar de sporthal toe omdat hij daar elke week gaat basketballen. Hij is elke week een uur onderweg, heen en terug. Hij maakt gebruik van WMO vervoer en de til service. Hij gaat van zijn speciaal onderwijs in Nieuw-West naar  zijn sportclub in zit in Noord. Dit is de enige plek met een rolstoel basketbalclub in de stad.',500, 1, 'Hoeveel inwoners in Amsterdam maken gebruik van de tilservice?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (3,0,'Een jonge man,  in een rolstoel, wil naar de sporthal toe omdat hij daar elke week gaat basketballen. Hij is elke week een uur onderweg, heen en terug. Hij maakt gebruik van WMO vervoer en de til service. Hij gaat van zijn speciaal onderwijs in Nieuw-West naar  zijn sportclub in zit in Noord. Dit is de enige plek met een rolstoel basketbalclub in de stad.',500, 1,'Hoeveel leerlingen gaan er naar een VSO in Nederland?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (4,0,'Een jonge man,  in een rolstoel, wil naar de sporthal toe omdat hij daar elke week gaat basketballen. Hij is elke week een uur onderweg, heen en terug. Hij maakt gebruik van WMO vervoer en de til service. Hij gaat van zijn speciaal onderwijs in Nieuw-West naar  zijn sportclub in zit in Noord. Dit is de enige plek met een rolstoel basketbalclub in de stad.',500,1,'Hoeveel bewoners van Gem. A''dam zitten er +//- op permanente basis in een rolstoel?');

INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (5,1,'Een jonge vrouw staat op een gecombineerde bus-/tramhalte. Ze is onderweg van haar huis in Amstelveen naar haar werk in het centrum van Amsterdam. Omdat ze een oogafwijking heeft opgelopen door diabetes is haar zicht beperkt en loopt ze met een geleide stok. Obstakels onderweg kan ze maar deels ontlopen.',1000,2,'De slechtziende vrouw dreigt tegen een onhandig neergezette koffer van een reiziger aan te lopen. Wat doe je?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (6,0,'Een jonge vrouw staat op een gecombineerde bus-/tramhalte. Ze is onderweg van haar huis in Amstelveen naar haar werk in het centrum van Amsterdam. Omdat ze een oogafwijking heeft opgelopen door diabetes is haar zicht beperkt en loopt ze met een geleide stok. Obstakels onderweg kan ze maar deels ontlopen.',500,2,'De vrouw hoort een bus aankomen en weet niet of dit de juiste is. Je ziet haar vertwijfeling. Wat doe je?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (7,1,'Een jonge vrouw staat op een gecombineerde bus-/tramhalte. Ze is onderweg van haar huis in Amstelveen naar haar werk in het centrum van Amsterdam. Omdat ze een oogafwijking heeft opgelopen door diabetes is haar zicht beperkt en loopt ze met een geleide stok. Obstakels onderweg kan ze maar deels ontlopen.',1000,2,'De vrouw komt bij een stoplicht waar de tikker stuk is. Wat doe jij?');

INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (8,0,'Een vrouw die bijna tegen haar pensioen aanloopt, heeft een tga (moment van bewusteloosheid / wegtrekker) gehad en raakt nog af en toe de kluts kwijt onderweg door de stad. Op station Zuid beleeft ze zo’n moment, ze weet niet goed waar ze is bij de overstap van de bus op de metro. Ook is ze slecht ter been',500,3,'Een oudere dame heeft een tga (moment van bewusteloosheid) en is daarna verward. Wat doe je?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (9,1,'Een vrouw die bijna tegen haar pensioen aanloopt, heeft een tga (moment van bewusteloosheid / wegtrekker) gehad en raakt nog af en toe de kluts kwijt onderweg door de stad. Op station Zuid beleeft ze zo’n moment, ze weet niet goed waar ze is bij de overstap van de bus op de metro. Ook is ze slecht ter been',1500,3,'De vrouw is ook slecht ter been en ziet een tram aankomen die echter door rijd. Wat doe je?');
INSERT INTO Question (`id`,`has_multiple_answers`,`info`,`points`,`block`,`question`) VALUES (10,0,'Een vrouw die bijna tegen haar pensioen aanloopt, heeft een tga (moment van bewusteloosheid / wegtrekker) gehad en raakt nog af en toe de kluts kwijt onderweg door de stad. Op station Zuid beleeft ze zo’n moment, ze weet niet goed waar ze is bij de overstap van de bus op de metro. Ook is ze slecht ter been',1500,3,'Als de vrouw in de tram stapt, blijkt het druk in de tram. De zitplaatsen bij de deur zijn bezet. Wat nu?');


-- INSERT INTO QUESTION OPTION
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (1,0,'15.000',1);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (2,0,'33.000',1);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (3,1,'40.000',1);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (4,0,'61.000',1);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (5,0,'20',2);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (6,0,'75',2);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (7,1,'135',2);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (8,0,'175',2);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (9,0,'24.140',3);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (10,1,'37.750',3);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (11,0,'42.430',3);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (12,0,'51.235',3);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (13,0,'500',4);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (14,0,'2.500',4);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (15,1,'8.000',4);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (16,0,'17.250',4);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (17,0,'Niets, Die vrouw heeft eens stok die redt zich wel',5);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (18,1,'Je vraagt aan de vrouw met de stok of ze hulp nodig heeft?',5);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (19,1,'Je vraagt aan de andere passagier om de koffer te verplaatsen?',5);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (20,0,'Je sprint tussen de vrouw en de koffer',5);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (21,0,'Niets. De buschauffeur ziet haar  staan met die stok, die zal wel roepen',6);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (22,0,'Niets. Aangezien ze met een stok reist heeft ze dat vast vaker gedaan',6);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (23,1,'Je vraagt haar op welke bus ze wacht',6);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (24,0,'og niets. Ik wacht af, in een uiterst geval schiet ik te hulp.',6);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (25,0,'Niets. Die vrouw komt toch vaker bij stoplichten zonder tikker',7);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (26,1,'Je maakt thuis een melding (MORA) van het defecte stoplicht',7);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (27,1,'Je vraagt of de vrouw hulp nodig heeft',7);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (28,0,'Niets. Jij wil zelf helemaal niet oversteken.',7);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (29,0,'Niets. Je ziet wel vaker verwarde mensen.',8);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (30,1,'Je vraagt of de vrouw weet waar ze is',8);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (31,0,'Je hoopt dat iemand er anders er op afloopt',8);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (32,0,'Je herkent niet dat de vrouw verward is.',8);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (33,0,'Niets. Ze rijden wel vaker door',9);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (34,1,'Je bonkt hard op het raam van de tram tot die stopt.',9);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (35,1,'Je besluit met de vrouw samen te wachten op de volgende tram',9);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (36,1,'Je gaat een kop koffie drinken met de vrouw (wachten duurt toch lang).',9);

INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (37,0,'Je vraagt meteen aan die mensen om op te staan',10);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (38,1,'Je vraagt aan die mensen om op te staan en gaat er dan zelf zitten',10);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (39,1,'Je hoopt dat de conducteur er wat van zegt.',10);
INSERT INTO question_option (`id`,`is_answer`,`option_answer`,`question`) VALUES (40,1,'Je hoopt dat de vrouw zelf mondig genoeg is.',10);


