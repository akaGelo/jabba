package ru.vyukov.jabba.agent;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import ru.vyukov.pojocli.annotations.CliParam;

@Data
public class MongoDbDatasourceConfig {

	@CliParam(name = "-host")
	private String host;

	@CliParam(name = "-port", requared = false)
	private int port = 27017;

	@CliParam(name = "-username", requared = false)
	private String username;

	@CliParam(name = "-password", requared = false)
	private String password;

	@CliParam(name = "-collections", requared = false,propertyType=String.class)
	private List<String> collections = Collections.emptyList();
}