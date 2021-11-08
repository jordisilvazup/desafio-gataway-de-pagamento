insert into restaurante  values(1,'Sabor em pedaços');
insert into restaurante  values(2,'Saborear');
insert into restaurante  values(3,'Estilo Mineiro');
insert into restaurante  values(4,'Dona Maria');
insert into restaurante  values(5,'Texas Bar e Restaurante');

insert into forma_de_pagamento values(1,'a vista','DINHEIRO');
insert into forma_de_pagamento values(2,'em 30 dias','CHEQUE');
insert into forma_de_pagamento values(3,'vista/prazo','MAQUINA');
insert into forma_de_pagamento values(4,'credito/debito','CARTAO');
insert into forma_de_pagamento values(5,'Cartao especial','CARTAO');

insert into restaurante_forma_de_pagamentos values(1,1);
insert into restaurante_forma_de_pagamentos values(1,2);
insert into restaurante_forma_de_pagamentos values(1,4);

insert into restaurante_forma_de_pagamentos values(2,1);
insert into restaurante_forma_de_pagamentos values(2,2);
insert into restaurante_forma_de_pagamentos values(2,3);

insert into restaurante_forma_de_pagamentos values(3,1);
insert into restaurante_forma_de_pagamentos values(3,2);
insert into restaurante_forma_de_pagamentos values(3,3);

insert into restaurante_forma_de_pagamentos values(4,1);
insert into restaurante_forma_de_pagamentos values(4,2);
insert into restaurante_forma_de_pagamentos values(4,3);


insert into restaurante_forma_de_pagamentos values(5,4);
insert into restaurante_forma_de_pagamentos values(5,3);
insert into restaurante_forma_de_pagamentos values(5,2);

insert into usuario(id,nome,email) values (1,'Joao Ferreira Marques','jfmarques@gmail.com');
insert into usuario(id,nome,email) values (2,'Fernado Coronel Souza','fernandinhobeiramar@gmail.com');
insert into usuario(id,nome,email) values (3,'Gabirel Antenor Silva Souza','gass@gmail.com');
insert into usuario(id,nome,email) values (4,'Luiza Maria de Abreu','lma@gmail.com');


insert into usuario_forma_de_pagamentos values(1,1);
insert into usuario_forma_de_pagamentos values(1,2);
insert into usuario_forma_de_pagamentos values(1,3);

insert into usuario_forma_de_pagamentos values(2,1);
insert into usuario_forma_de_pagamentos values(2,2);
insert into usuario_forma_de_pagamentos values(2,3);
insert into usuario_forma_de_pagamentos values(2,4);

insert into usuario_forma_de_pagamentos values(3,1);
insert into usuario_forma_de_pagamentos values(3,2);
insert into usuario_forma_de_pagamentos values(3,3);
insert into usuario_forma_de_pagamentos values(3,4);

insert into usuario_forma_de_pagamentos values(4,1);
insert into usuario_forma_de_pagamentos values(4,2);
insert into usuario_forma_de_pagamentos values(4,3);
insert into usuario_forma_de_pagamentos values(4,4);

insert into fraude values (1,'fernandinhobeiramar@gmail.com');

insert into pedido(id,valor,forma_de_pagamento_id,restaurante_id,usuario_id) values (1,3.5,1,1,1);
insert into pedido(id,valor,forma_de_pagamento_id,restaurante_id,usuario_id) values (2,2.5,1,1,4);
insert into pedido(id,valor,forma_de_pagamento_id,restaurante_id,usuario_id) values (3,2.5,1,1,3);
insert into pedido(id,valor,forma_de_pagamento_id,restaurante_id,usuario_id) values (4,2.5,1,1,2);

insert into gateway values (1,'SEYA');
insert into gateway values (2,'SAORI');
insert into gateway values (3,'TANGO');