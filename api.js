const { VarChar } = require("msnodesqlv8");
const sql = require("mssql");

const config = {
  user: "omerutkudemirr",
  password: "123456",
  server: "LAPTOP-6GU3D2FV\\SQLEXPRESS",
  database: "words",
  port: parseInt(process.env.DB_PORT, 1433),
  options: {
    encrypt: false,
    enableArithAbort: true,
  },
};

// SQL bağlantı havuzunu oluştur
const pool = new sql.ConnectionPool(config);
const poolConnect = pool.connect();




async function getKullanicilarBildikleri() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.UserWord");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}
async function updateKullaniciBildikleri(newData) {
  try {
   const { userID,en,tr,sayi,tarih} = newData;
      // SQL Server veritabanına bağlanın
      const pool = await sql.connect(config);

      // Veritabanında ilgili kaydı güncelleyin
      const result = await pool.request()
          .input('userID', sql.Int, userID)
          .input('en', sql.NVarChar, en)
          .input('tarih',sql.VarChar,tarih)
          .input('sayi', sql.Int, sayi)
          .query('UPDATE dbo.UserWord SET sayi = @sayi ,tarih=@tarih WHERE userID = @userID AND en = @en');

      console.log('Data updated successfully:', result);
      return result;
  } catch (err) {
      console.error('Error updating data:', err);
      throw err; // Hata fırlat
  }
}


async function updateKullanicilar(newData) {
  try {
   const { ad,mail,sifre} = newData;
      // SQL Server veritabanına bağlanın
      const pool = await sql.connect(config);

      // Veritabanında ilgili kaydı güncelleyin
      const result = await pool.request()
          .input('ad',sql.VarChar,ad)
          .input('mail', sql.VarChar, mail)
          .input('sifre',sql.VarChar,sifre)
          
          .query('UPDATE dbo.Users SET sifre = @sifre  WHERE mail = @mail');

      console.log('Data updated successfully:', result);
      return result;
  } catch (err) {
      console.error('Error updating data:', err);
      throw err; // Hata fırlat
  }
}

async function setKullaniciBildikleri(kelime) {
  const { userID,en,tr,sayi,tarih,imageUri} = kelime;

  try {
      const pool = await sql.connect(); // Get the connection pool

      const result = await pool.request()
          
          .input('userID', sql.Int, userID)
          .input('en', sql.VarChar, en)
          .input('tr', sql.VarChar, tr)
          .input('sayi', sql.VarChar, sayi)
          .input('tarih',sql.VarChar,tarih)
          .input('imageUri',sql.VarChar,imageUri)
          .query('INSERT INTO dbo.UserWord(userID,en,tr,sayi,tarih,imageUri) VALUES (@userID,@en,@tr,@sayi,@tarih,@imageUri)');

      console.log('Data inserted successfully:', result);
  } catch (err) {
      console.error('Error inserting data:', err);
  }
}

async function setKullaniciTamamenBildikleri(kelime) {
  const { userID,en,tr,sayi,tarih} = kelime;

  try {
      const pool = await sql.connect(); // Get the connection pool

      const result = await pool.request()
          
          .input('userID', sql.Int, userID)
          .input('en', sql.VarChar, en)
          .input('tr', sql.VarChar, tr)
          .input('sayi', sql.VarChar, sayi)
          .input('tarih',sql.VarChar,tarih)
          .query('INSERT INTO dbo.UserKnownWords(userID,en,tr,sayi,tarih) VALUES (@userID,@en,@tr,@sayi,@tarih)');

      console.log('Data inserted successfully:', result);
  } catch (err) {
      console.error('Error inserting data:', err);
  }
}

async function getFiil() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.fiil");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}


async function getBasicWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.basic_words");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function setBasicWords(kelime) {
  const { userID,en,tr,sayi,tarih,imageUri} = kelime;

  try {
      const pool = await sql.connect(); // Get the connection pool

      const result = await pool.request()
          
          .input('userID', sql.Int, userID)
          .input('en', sql.VarChar, en)
          .input('tr', sql.VarChar, tr)
          .input('sayi', sql.VarChar, sayi)
          .input('tarih',sql.VarChar,tarih)
          .input('imageUri',sql.VarChar,imageUri)
          .query('INSERT INTO dbo.basic_words(userID,en,tr,sayi,tarih,imageUri) VALUES (@userID,@en,@tr,@sayi,@tarih,@imageUri)');

      console.log('Data inserted successfully:', result);
  } catch (err) {
      console.error('Error inserting data:', err);
  }
}

async function getMidLevelWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.mid_words");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getHardLevelWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.hard_words");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}

async function getHardCoreWords() {
  await poolConnect;
  try {
    const result = await pool.request().query("SELECT * FROM dbo.hard_core_words");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  }
}
async function getKullanicilar() {
  try {
    await sql.connect(config);
    const result = await sql.query("SELECT * FROM dbo.Users");
    return result.recordset;
  } catch (err) {
    console.error("SQL error", err);
  } 
}


async function getKullaniciById(userId) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.Users WHERE id = ${userId}`); // Kullanıcıyı ID'ye göre sorgula
    return result.recordset[0]; // İlk kullanıcıyı döndür
  } catch (err) {
    console.error("SQL hatası:", err);
    throw err;
  }
}
async function getKelimeById(userID) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.UserWord WHERE userID = ${userID}`); // Kullanıcıyı ID'ye göre sorgula
    return result.recordset; // İlk kullanıcıyı döndür
  } catch (err) {
    console.error("SQL hatası:", err);
    throw err;
  }
}


async function setKullanicilar(kullanici) {
  const { ad, soyad, mail, sifre } = kullanici;

  try {
      const pool = await sql.connect(); // Get the connection pool

      const result = await pool.request()

          .input('ad', sql.VarChar, ad)
          .input('soyad', sql.VarChar, soyad)
          .input('mail', sql.VarChar, mail)
          .input('sifre', sql.VarChar, sifre)
          .query('INSERT INTO dbo.Users(ad, soyad, mail, sifre) VALUES (@ad, @soyad, @mail, @sifre)');

      console.log('Data inserted successfully:', result);
  } catch (err) {
      console.error('Error inserting data:', err);
  }
}

async function getKullaniciBilgileri(userId) {
  try {
    await sql.connect(config);
    const result = await sql.query(`SELECT * FROM dbo.UserWord WHERE id = ${userId}`); // Kullanıcının bilgilerini ID'ye göre sorgula
    return result.recordset; // Kullanıcı bilgilerini döndür
  } catch (err) {
    console.error("SQL hatası:", err);
    throw err;
  }
}


module.exports = {
  getBasicWords,
  getMidLevelWords,
  getHardLevelWords,
  getHardCoreWords,
  getFiil,
  getKullanicilar,
  getKullanicilarBildikleri,
  setKullanicilar,
  getKullaniciById,
  getKullaniciBilgileri,
  setKullaniciBildikleri,
  getKelimeById,
  updateKullaniciBildikleri,
  setKullaniciTamamenBildikleri,
  setBasicWords,
  updateKullanicilar
};
