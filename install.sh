#!/bin/bash

# Stop the script if any command fails
set -e

# Step 1: Build the project using Maven
echo "Building the project using Maven..."
mvn clean package assembly:single

# Check if the build was successful
JAR_FILE="target/analyzer-jar-with-dependencies.jar"
if [[ ! -f "$JAR_FILE" ]]; then
  echo "Error: JAR file not found after the build."
  exit 1
fi

# Step 2: Copy the JAR file to /usr/local/bin
echo "Installing the JAR file..."
INSTALL_DIR="/usr/local/bin"
INSTALL_JAR="$INSTALL_DIR/analyzer.jar"
sudo cp "$JAR_FILE" "$INSTALL_JAR"

# Step 3: Create a launch script and symbolic link
echo "Creating a symbolic link to launch 'analyzer'..."
sudo bash -c 'cat > /usr/local/bin/analyzer << EOF
#!/bin/bash
java -jar '"$INSTALL_JAR"' "\$@"
EOF'
sudo chmod +x /usr/local/bin/analyzer

echo "Installation complete. You can now run the utility using the 'analyzer' command."
