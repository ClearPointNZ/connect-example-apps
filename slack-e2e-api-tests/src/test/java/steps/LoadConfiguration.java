package steps;

import cucumber.api.java.Before;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoadConfiguration {

  private static boolean loadedOnce = false;

  @Before
  public void beforeFeatures() {
    if (!loadedOnce) {
      try {

        loadConfiguration();
        loadedOnce = true;
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(String.format("You do not have a configuration file in `%s`. Please specify one there or use -DcukeConfig to manually specify one.", getConfigFilePath()), e);
      }
    }
  }


  private void loadConfiguration() throws IOException {
    File workingFolder = new File("src/test/resources");
    if (!workingFolder.exists()) {
      throw new RuntimeException("Please run this test with the working folder set to the project otherwise it won't pick up your configuration");
    }

    String path = getConfigFilePath();

    if (!new File(path).exists()) {
      throw new RuntimeException("Missing configuration " + path);
    }

    loadYamlFile(new File(path));

  }

  private void loadYamlFile(File yamlFile) {
    Yaml parser = new Yaml();

    try {
      Map<String, Object> yamlProperties = (Map<String, Object>) parser.load(new FileReader(yamlFile));
      Map<String, Object> result = new LinkedHashMap<>();

      buildFlattenedMap(result, yamlProperties, null);

      result.forEach((key, value) -> System.setProperty(key, value.toString()));

    } catch (FileNotFoundException e) {
      System.out.println("Unable to find file " + yamlFile.getAbsolutePath());

    }
  }


  /*
   * From Spring Boot's YamlPropertySourceLoader with List modifications
   */
  private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
    for (Map.Entry<String, Object> entry : source.entrySet()) {
      String key = entry.getKey();
      if (path != null) {

        if (key.startsWith("[")) {
          key = path + key;
        } else {
          key = path + "." + key;
        }
      }

      Object value = entry.getValue();
      if (value instanceof String) {
        result.put(key, value);
      } else if (value instanceof Map) {
        // Need a compound key
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) value;
        buildFlattenedMap(result, map, key);
      } else if (value instanceof Collection) {
        // Need a compound key
        @SuppressWarnings("unchecked")
        Collection<Object> collection = (Collection<Object>) value;
        boolean allSimple = collection.stream().allMatch(o -> o != null && o.getClass().getName().startsWith("java.lang."));
        if (allSimple) {
          result.put(key, collection.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(",")));
        } else {
          int count = 0;
          for (Object object : collection) {
            buildFlattenedMap(result,
              Collections.singletonMap("[" + (count++) + "]", object), key);
          }
        }
      } else {
        result.put(key, value != null ? value : "");
      }
    }
  }

  private String getConfigFilePath() {
    return System.getProperty("cukeConfig", "src/test/resources/env.yml");
  }

}
