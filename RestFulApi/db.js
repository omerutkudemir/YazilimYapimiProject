const { VarChar } = require("msnodesqlv8");
const sql = require("mssql");
const config = {
  user: "admin",
  password: "admin",
  server: "LAPTOP-6GU3D2FV\\SQLEXPRESS",
  database: "words",
  port: parseInt(process.env.DB_PORT, 1433),
  options: {
    encrypt: false,
    enableArithAbort: true,
  },
};

const pool = new sql.ConnectionPool(config);
const poolConnect = pool.connect();

async function getUserKnownWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.UserWord");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function updateUserKnownWords(newData) {
  try {
    const { userID, en, tr, count, date } = newData;
    const pool = await sql.connect(config);
    const result = await pool.request()
      .input('userID', sql.Int, userID)
      .input('en', sql.NVarChar, en)
      .input('date', sql.VarChar, date)
      .input('count', sql.Int, count)
      .query('UPDATE dbo.UserWord SET count = @count, date = @date WHERE userID = @userID AND en = @en');
    console.log('Data updated successfully:', result);
    return result;
  } catch (err) {
    console.error('Error updating data:', err);
    throw err;
  }
}

async function updateUser(newData) {
  try {
    const { name, email, password } = newData;
    const pool = await sql.connect(config);
    const result = await pool.request()
      .input('name', sql.VarChar, name)
      .input('email', sql.VarChar, email)
      .input('password', sql.VarChar, password)
      .query('UPDATE dbo.Users SET password = @password WHERE email = @email');
    console.log('Data updated successfully:', result);
    return result;
  } catch (err) {
    console.error('Error updating data:', err);
    throw err;
  }
}

async function setUserKnownWords(word) {
  const { userID, en, tr, count, date, imageUri } = word;
  try {
    const pool = await sql.connect();
    const result = await pool.request()
      .input('userID', sql.Int, userID)
      .input('en', sql.VarChar, en)
      .input('tr', sql.VarChar, tr)
      .input('count', sql.VarChar, count)
      .input('date', sql.VarChar, date)
      .input('imageUri', sql.VarChar, imageUri)
      .query('INSERT INTO dbo.UserWord(userID, en, tr, count, date, imageUri) VALUES (@userID, @en, @tr, @count, @date, @imageUri)');
    console.log('Data inserted successfully:', result);
  } catch (err) {
    console.error('Error inserting data:', err);
  }
}

async function setUserCompletelyKnownWords(word) {
  const { userID, en, tr, count, date } = word;
  try {
    const pool = await sql.connect();
    const result = await pool.request()
      .input('userID', sql.Int, userID)
      .input('en', sql.VarChar, en)
      .input('tr', sql.VarChar, tr)
      .input('count', sql.VarChar, count)
      .input('date', sql.VarChar, date)
      .query('INSERT INTO dbo.UserKnownWords(userID, en, tr, count, date) VALUES (@userID, @en, @tr, @count, @date)');
    console.log('Data inserted successfully:', result);
  } catch (err) {
    console.error('Error inserting data:', err);
  }
}

async function getVerbs() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.Verbs");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getBasicWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.BasicWords");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function setBasicWords(word) {
  const { userID, en, tr, count, date, imageUri } = word;
  try {
    const pool = await sql.connect();
    const result = await pool.request()
      .input('userID', sql.Int, userID)
      .input('en', sql.VarChar, en)
      .input('tr', sql.VarChar, tr)
      .input('count', sql.VarChar, count)
      .input('date', sql.VarChar, date)
      .input('imageUri', sql.VarChar, imageUri)
      .query('INSERT INTO dbo.BasicWords(userID, en, tr, count, date, imageUri) VALUES (@userID, @en, @tr, @count, @date, @imageUri)');
    console.log('Data inserted successfully:', result);
  } catch (err) {
    console.error('Error inserting data:', err);
  }
}

async function getIntermediateWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.IntermediateWords");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getHardWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.HardWords");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getHardCoreWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.HardCoreWords");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getUsers() {
  try {
    await sql.connect(config);
    const result = await sql.query("SELECT * FROM dbo.Users");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getUserById(userId) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.Users WHERE id = ${userId}`);
    return result.recordset[0];
  } catch (err) {
    console.error("SQL error:", err);
    throw err;
  }
}

async function getWordById(userID) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.UserWord WHERE userID = ${userID}`);
    return result.recordset;
  } catch (err) {
    console.error("SQL error:", err);
    throw err;
  }
}

async function setUsers(user) {
  const { firstName, lastName, email, password } = user;
  try {
    const pool = await sql.connect();
    const result = await pool.request()
      .input('firstName', sql.VarChar, firstName)
      .input('lastName', sql.VarChar, lastName)
      .input('email', sql.VarChar, email)
      .input('password', sql.VarChar, password)
      .query('INSERT INTO dbo.Users(firstName, lastName, email, password) VALUES (@firstName, @lastName, @email, @password)');
    console.log('Data inserted successfully:', result);
  } catch (err) {
    console.error('Error inserting data:', err);
  }
}

async function getUserDetails(userId) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.UserWord WHERE id = ${userId}`);
    return result.recordset;
  } catch (err) {
    console.error("SQL error:", err);
    throw err;
  }
}

module.exports = {
  getBasicWords,
  getIntermediateWords,
  getHardWords,
  getHardCoreWords,
  getVerbs,
  getUsers,
  getUserKnownWords,
  setUsers,
  getUserById,
  getUserDetails,
  setUserKnownWords,
  getWordById,
  updateUserKnownWords,
  setUserCompletelyKnownWords,
  setBasicWords,
  updateUser
};
