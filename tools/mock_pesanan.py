"""Mock REST API lokal untuk mencoba daftar dan form pesanan."""

import argparse
import json
from http.server import BaseHTTPRequestHandler, HTTPServer
from time import sleep
from urllib.parse import urlsplit


PESANAN = [
    dict(id=1, nama="Ayu", pesanan="Nasi Ayam", lokasi="Gedung C",
         status="Menunggu konfirmasi", catatan=None),
    dict(id=2, nama="Raka", pesanan="Es Teh", lokasi="Perpustakaan",
         status="Selesai", catatan="Tanpa gula"),
]


class Handler(BaseHTTPRequestHandler):
    def kirim_json(self, status, data):
        body = json.dumps(data, ensure_ascii=False).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def do_GET(self):
        path = urlsplit(self.path).path
        if path == "/api/pesanan":
            sleep(self.server.delay)
            self.kirim_json(200, PESANAN)
            return

        item = next((item for item in PESANAN if path == f"/api/pesanan/{item['id']}"), None)
        if item is None:
            self.kirim_json(404, {"pesan": "Pesanan tidak ditemukan."})
        else:
            self.kirim_json(200, item)

    def do_POST(self):
        if urlsplit(self.path).path != "/api/pesanan":
            self.kirim_json(404, {"pesan": "Rute tidak ditemukan."})
            return

        try:
            panjang = int(self.headers.get("Content-Length", "0"))
            if not 1 <= panjang <= 4096:
                raise ValueError()
            data = json.loads(self.rfile.read(panjang))
        except (ValueError, UnicodeError):
            self.kirim_json(400, {"pesan": "Isi permintaan tidak valid."})
            return

        kolom = ("nama", "pesanan", "lokasi")
        if not isinstance(data, dict) or any(
            not isinstance(data.get(k), str) or not data[k].strip() for k in kolom
        ):
            self.kirim_json(400, {"pesan": "Nama, pesanan, dan lokasi wajib diisi."})
            return

        catatan = data.get("catatan")
        if catatan is not None and not isinstance(catatan, str):
            self.kirim_json(400, {"pesan": "Catatan harus berupa teks."})
            return

        item = {k: data[k].strip() for k in kolom}
        item.update(id=PESANAN[-1]["id"] + 1, status="Menunggu konfirmasi", catatan=catatan)
        PESANAN.append(item)
        self.kirim_json(201, item)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--port", type=int, default=8080)
    parser.add_argument("--delay", type=float, default=0, help="Jeda GET daftar dalam detik (0–8)")
    args = parser.parse_args()
    if not 0 <= args.delay <= 8:
        parser.error("--delay harus antara 0 dan 8 detik")

    with HTTPServer(("127.0.0.1", args.port), Handler) as server:
        server.delay = args.delay
        print(f"Mock API: http://127.0.0.1:{args.port}/api/pesanan", flush=True)
        try:
            server.serve_forever()
        except KeyboardInterrupt:
            pass


if __name__ == "__main__":
    main()
