const express = require("express");
const app = express();
const http = require("http");
const { 
  getBasicWords,
  getVerbs,
  getUsers,
  getUserKnownWords,
  setUsers,
  getUserById,
  setUserKnownWords,
  getWordById,
  updateUserKnownWords,
  setUserMasteredWords,
  setBasicWords,
  updateUsers
} = require("./api");

app.use(express.json());

app.get("/", (req, res) => {
  res.send("Main Page");
});

app.get("/users", async (req, res) => {
  const users = await getUsers();
  res.send(users);
});

app.get("/users/known_words", async (req, res) => {
  const userKnownWords = await getUserKnownWords();
  res.send(userKnownWords);
});

app.get("/users/:userID/known_words", async (req, res) => {
  const userId = req.params.userID; // Get user ID
  try {
    const userInfo = await getWordById(userId); // Get user information
    if (userInfo) {
      res.status(200).send(userInfo); // Send user information
    } else {
      res.status(404).send({ message: "User information not found" }); // Send 404 if user information is not found
    }
  } catch (err) {
    console.error("Error:", err);
    res.status(500).send({ message: "Server error" }); // Send 500 if there is a server error
  }
});

app.post("/users/known_words", async (req, res) => {
  const request = req.body;
  console.log(request);
  await setUserKnownWords(request);
  const users = await getUserKnownWords();
  // Send a response indicating the operation was successful
  res.status(200).send({ message: "Known words successfully added" });
});

app.post("/basicwords", async (req, res) => {
  const request = req.body;
  console.log(request);
  await setBasicWords(request);
  const users = await getBasicWords();
  // Send a response indicating the operation was successful
  res.status(200).send({ message: "Basic words successfully added" });
});

app.post("/users/mastered_words", async (req, res) => {
  const request = req.body;
  console.log(request);
  try {
    await setUserMasteredWords(request);
    const users = await getUserKnownWords();
    // Send a response indicating the operation was successful
    res.status(200).send({ message: "Mastered words successfully added" });
  } catch (error) {
    console.error("Error processing request:", error);
    res.status(500).send({ message: "An unknown error occurred." });
  }
});

app.put('/users/known_words', async(req, res) => {
  const request = req.body;
  console.log(request);
  await updateUserKnownWords(request);
  const users = await getUserKnownWords();
  res.status(200).send({ message: "Known words successfully updated" });
});

app.put('/users', async(req, res) => {
  const request = req.body;
  console.log(request);
  await updateUsers(request);
  res.status(200).send({ message: "Password updated successfully" });
});

app.get("/verbs", async (req, res) => {
  const verbs = await getVerbs();
  res.send(verbs);
});

app.get("/basicwords", async (req, res) => {
  const basicWords = await getBasicWords();
  res.send(basicWords);
});

app.post("/users", async (req, res) => {
  const request = req.body;
  await setUsers(request);
  const users = await getUsers();
  // Send a response indicating the operation was successful
  res.status(200).send({ message: "User successfully added" });
});

app.get("/users/:id", async (req, res) => {
  const userId = req.params.id; // Get user ID
  try {
    const user = await getUserById(userId); // Get user by ID
    if (user) {
      res.status(200).send(user); // Send user if found
    } else {
      res.status(404).send({ message: "User not found" }); // Send 404 if user is not found
    }
  } catch (err) {
    console.error("Error:", err);
    res.status(500).send({ message: "Server error" }); // Send 500 if there is a server error
  }
});

const PORT = 8080;
app.listen(PORT, () => {
  console.log(`App is running on port ${PORT}`);
});
