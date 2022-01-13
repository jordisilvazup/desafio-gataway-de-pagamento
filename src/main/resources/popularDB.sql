-- execute esse scprit no seu SGBD após iniciar a aplicação


insert into forma_de_pagamento values(1,'a vista','DINHEIRO');
insert into forma_de_pagamento values(2,'em 30 dias','CHEQUE');
insert into forma_de_pagamento values(3,'vista/prazo','MAQUINA');
insert into forma_de_pagamento values(4,'credito/debito','CARTAO');
insert into forma_de_pagamento values(5,'Cartao especial','CARTAO');

insert into gateway values (1,'SEYA');
insert into gateway values (2,'SAORI');
insert into gateway values (3,'TANGO');

-- usuario
insert into usuario (id,nome,email)
select i as id,
left(md5(random()::text), 6) as nome,
md5(random()::text) || '@zup.com.br' as email
from generate_series(1, 1000000) as i;

-- restaurante

insert into restaurante (id,nome)
select i as id,
left(md5(random()::text), 6) as nome
from generate_series(1,1000000) as i;

-- restaurante e forma de pagamentos
insert into restaurante_forma_de_pagamentos (restaurante_id ,forma_de_pagamentos_id)
select i as restaurante_id,
j as forma_de_pagamentos_id
from generate_series(1,1000000) as i, generate_series(1,5) as j


--usuario e formas de pagamento
insert into usuario_forma_de_pagamentos (usuario_id ,forma_de_pagamentos_id)

select i as usuario_id,
j as forma_de_pagamentos_id
from generate_series(1,1000000) as i, generate_series(1,5) as j


-- inserir pedido
insert into pedido(id,valor,forma_de_pagamento_id,restaurante_id,usuario_id)
select
	 i as id,
	random() as valor,
	floor(random() * (6 - 1)+ 1) as forma_de_pagamento_id,
	floor(random() * (1000001 - 1)+ 1) as restaurante_id,
	floor(random() * (1000001 - 1)+ 1) as usuario_id
from
	generate_series(1, 1000000) as i