package ru.vyukov.jabba.agent.cli;

import lombok.Data;
import ru.vyukov.pojocli.annotations.CliParam;

@Data
public class DataSourceTypeConfig {

	@CliParam(name = "-type", requared = true, help = "Database type. Example: mongodb")
	private Types datasourceType;

	public enum Types {
		mongodb, postgresql, mysql
	}

}
