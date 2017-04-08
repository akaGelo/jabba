package ru.vyukov.jabba.agent.cli;

import lombok.Data;
import ru.vyukov.pojocli.annotations.CliParam;

/**
 * Configuration properties for deamon mode
 * @author gelo
 *
 */
@Data
public class RunTypeConfig {

	@CliParam(name = "-daemon", help = "run agent as daemon", requared = false)
	private boolean daemon = true;
}
