const express = require("express");
const app = express();
const http=require("http")
const { 
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
  
} = require("./api");

app.use(express.json());

app.get("/", (req, res) => {
  res.send("Main Page");
});
app.get("/kullanicilar", async (req, res) => {
  const kullanicilar = await getKullanicilar();
  res.send(kullanicilar);
});

app.get("/kullanicilar/bildikleri", async (req, res) => {
  const kullanicilarBildikleri = await getKullanicilarBildikleri();
  res.send(kullanicilarBildikleri);
});

app.get("/kullanicilar/:userID/bildikleri", async (req, res) => {
  const userId = req.params.userID; // Kullanıcı ID'sini al
  try {
    const kullaniciBilgileri = await getKelimeById(userId); // Kullanıcı bilgilerini al
    if (kullaniciBilgileri) {
      res.status(200).send(kullaniciBilgileri); // Kullanıcı bilgilerini gönder
    } else {
      res.status(404).send({ message: "Kullanıcı bilgileri bulunamadı" }); // Kullanıcı bilgileri bulunamazsa 404 hatası gönder
    }
  } catch (err) {
    console.error("Hata:", err);
    res.status(500).send({ message: "Sunucu hatası" }); // Sunucu hatası varsa 500 hatası gönder
  }
});

app.post("/kullanicilar/bildikleri", async (req, res) => {
  const request = req.body;
  console.log(request)
  await setKullaniciBildikleri(request);
  const kullancilar = await getKullanicilarBildikleri();

  // İşlemin başarılı olduğunu belirten bir yanıt gönder
  res.status(200).send({ message: "bilinen kelimeler başarıyla eklendi" });
});

app.post("/basicwords", async (req, res) => {
  const request = req.body;
  console.log(request)
  await setBasicWords(request);
  const kullancilar = await getBasicWords();

  // İşlemin başarılı olduğunu belirten bir yanıt gönder
  res.status(200).send({ message: "bilinen kelimeler başarıyla eklendi" });
});


app.post("/kullanicilar/tamamen_bildikleri", async (req, res) => {
  const request = req.body;
  console.log(request);
  try {
    await setKullaniciTamamenBildikleri(request);
    const kullanicilar = await getKullanicilarBildikleri();

    // İşlemin başarılı olduğunu belirten bir yanıt gönder
    res.status(200).send({ message: "bilinen kelimeler başarıyla eklendi" });
  } catch (error) {
    console.error("Error processing request:", error);
    res.status(500).send({ message: "Bilinmeyen bir hata oluştu." });
  }
});
app.put('/kullanicilar/bildikleri', async(req, res) => {
  const request = req.body 
  console.log(request)
  await updateKullaniciBildikleri(request)
  const kullancilar = await getKullanicilarBildikleri();
  res.status(200).send({ message: "bilinen kelimeler başarıyla güncellendi" });

  
});

app.put('/kullanicilar', async(req, res) => {
  const request = req.body 
  console.log(request)
  await updateKullanicilar(request)
  
  res.status(200).send({ message: "sifre guncellendi" });

  
});


app.get("/fiil", async (req, res) => {
  const fiiller = await getFiil();
  res.send(fiiller);
});

app.get("/basicwords", async (req, res) => {
  const basitKelimeler = await getBasicWords();
  res.send(basitKelimeler);
});

app.get("/midwords", async (req, res) => {
  const ortakelimeler = await getMidLevelWords();
  res.send(ortakelimeler);
});

app.get("/hardwords", async (req, res) => {
  const zorkelimeler = await getHardLevelWords();
  res.send(zorkelimeler);
});

app.get("/hardcorewords", async (req, res) => {
  const cokzorkelimeler = await getHardCoreWords();
  res.send(cokzorkelimeler);
});

app.post("/kullanicilar", async (req, res) => {
  const request = req.body;
  await setKullanicilar(request);
  const kullancilar = await getKullanicilar();

  // İşlemin başarılı olduğunu belirten bir yanıt gönder
  res.status(200).send({ message: "Kullanıcı başarıyla eklendi" });
});


app.get("/kullanicilar/:id", async (req, res) => {
  const userId = req.params.id; // Kullanıcı ID'sini al
  try {
    const kullanici = await getKullaniciById(userId); // ID'ye göre kullanıcıyı al
    if (kullanici) {
      res.status(200).send(kullanici); // Kullanıcı bulunduysa gönder
    } else {
      res.status(404).send({ message: "Kullanıcı bulunamadı" }); // Kullanıcı bulunamadıysa 404 hatası gönder
    }
  } catch (err) {
    console.error("Hata:", err);
    res.status(500).send({ message: "Sunucu hatası" }); // Sunucu hatası varsa 500 hatası gönder
  }
});




const PORT = 8080;
app.listen(PORT, () => {
  console.log(`App is running on port ${PORT}`);
});
