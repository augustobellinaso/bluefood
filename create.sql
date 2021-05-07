create table categoria_restaurante (id integer not null auto_increment, imagem varchar(50) not null, nome varchar(20) not null, primary key (id)) engine=InnoDB;
create table cliente (id integer not null auto_increment, email varchar(60) not null, nome varchar(80) not null, senha varchar(80) not null, telefone varchar(11) not null, cep varchar(8) not null, cpf varchar(11) not null, primary key (id)) engine=InnoDB;
create table item_cardapio (id integer not null auto_increment, categoria varchar(25), descricao varchar(80), destaque bit not null, imagem varchar(50), nome varchar(50), preco decimal(19,2) not null, restaurante_id integer not null, primary key (id)) engine=InnoDB;
create table item_pedido (ordem integer not null, observacoes varchar(100), preco decimal(19,2) not null, quantidade integer not null, pedido_id integer not null, item_cardapio_id integer not null, primary key (ordem, pedido_id)) engine=InnoDB;
create table pagamento (bandeira_cartao varchar(10) not null, data datetime not null, num_cartao_final varchar(4) not null, pedido_id integer not null, primary key (pedido_id)) engine=InnoDB;
create table pedido (id integer not null auto_increment, data datetime not null, status integer not null, subtotal decimal(19,2) not null, taxa_entrega decimal(19,2) not null, total decimal(19,2) not null, cliente_id integer not null, restaurante_id integer not null, primary key (id)) engine=InnoDB;
create table restaurante (id integer not null auto_increment, email varchar(60) not null, nome varchar(80) not null, senha varchar(80) not null, telefone varchar(11) not null, cnpj varchar(14) not null, logotipo varchar(80), taxa_entrega decimal(19,2) not null, tempo_entrega_base integer not null, primary key (id)) engine=InnoDB;
create table restaurante_has_categoria (restaurante_id integer not null, categoria_restaurante_id integer not null, primary key (restaurante_id, categoria_restaurante_id)) engine=InnoDB;
alter table item_cardapio add constraint FKknlisgqcckitrcm7ip9de1vsp foreign key (restaurante_id) references restaurante (id);
alter table item_pedido add constraint FK60ym08cfoysa17wrn1swyiuda foreign key (pedido_id) references pedido (id);
alter table item_pedido add constraint FKa95sxu9i9r05tlc1auc8xhcvg foreign key (item_cardapio_id) references item_cardapio (id);
alter table pagamento add constraint FKthad9tkw4188hb3qo1lm5ueb0 foreign key (pedido_id) references pedido (id);
alter table pedido add constraint FK30s8j2ktpay6of18lbyqn3632 foreign key (cliente_id) references cliente (id);
alter table pedido add constraint FK3eud5cqmgsnltyk704hu3qj71 foreign key (restaurante_id) references restaurante (id);
alter table restaurante_has_categoria add constraint FKfe1q7ado3x5ficku6i2n7094s foreign key (categoria_restaurante_id) references categoria_restaurante (id);
alter table restaurante_has_categoria add constraint FK6gsespjmfo51xubsp917nggnh foreign key (restaurante_id) references restaurante (id);
