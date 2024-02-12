# AI Service with OpenAI

This project is a Java-based AI service using the OpenAI API for natural language processing.

## CURRENTLY NOT HOSTED ON ANY SERVER


## Usage

1. **Create a Thread:**
   ```bash
   curl -X GET https://your.domain/ai/thread
2. **Ask a Question:**
   ```bash
      curl -X GET https://your.domain/ai/{your_question}/{thread_id}


OPENAI_API_KEY: Currently using my api Key but feel free to clone the project and use your own API Key.

HTTP_PLATFORM_PORT: The port on which your Java application is configured to run.

**If you want to use your own OpenAI API key, follow these steps:**
## Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Keith-Hughes/KeithAi_api.git
   cd ai-service
2. Obtain an OpenAI API key from https://beta.openai.com/account/api-keys.
3. Create an instance of AiAssistant in the AiService class, replacing the OpenAI API key with your own:
    ```bash
      this.aiAssistant = new AiAssistant("YOUR_OWN_OPENAI_API_KEY");
4. Replace Assistant ID with your Assistant ID.

   
## Local Development
For local development, you can follow the steps mentioned in the Setup section of this README. Make sure to set your OpenAI API key and adjust the base URL accordingly.

Dependencies
Javalin: Web framework for Java.
OpenAI API: API for natural language processing.
Contributors
Keith Hughes mrkphughes@gmail.com
