case object Configurations {

  private val extractStorageUri = "abfss://<your-container-name>@<your-storage-account-name>.dfs.core.windows.net"

  private val loadStorageUri = extractStorageUri

  val inputDirectory = extractStorageUri + "/input"

  val outputDirectory = loadStorageUri + "/output"

  val azureConfigs = Map(
    "fs.azure.account.auth.type.<your-storage-account-name>.dfs.core.windows.net" -> "OAuth",
    "fs.azure.account.oauth.provider.type.<your-storage-account-name>.dfs.core.windows.net" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
    "fs.azure.account.oauth2.client.id.<your-storage-account-name>.dfs.core.windows.net" -> "<your-client-id>",
    "fs.azure.account.oauth2.client.secret.<your-storage-account-name>.dfs.core.windows.net" -> "<your-client-secret>",
    "fs.azure.account.oauth2.client.endpoint.<your-storage-account-name>.dfs.core.windows.net" -> "https://login.microsoftonline.com/<your-tenant-id>/oauth2/token",
    "fs.azure.account.auth.type.<your-storage-account-name>.dfs.core.windows.net" -> "SharedKey",
    "fs.azure.account.key.<your-storage-account-name>.dfs.core.windows.net" -> "<your-storage-account-key>")

}
