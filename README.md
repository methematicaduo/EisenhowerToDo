# Eisenhower To-Do App

Minimal ve modern bir Android mobil uygulama. Uygulama, Eisenhower Matrisi yöntemiyle görevleri dört kategoriye ayırıyor:

## Özellikler

- **4 Kategori**: "Acil & Önemli", "Acil ama Önemli Değil", "Önemli ama Acele Değil", "Acil & Önemli Değil"
- **Minimal Tasarım**: Siyah-beyaz renk paleti ile temiz ve modern arayüz
- **Görev Yönetimi**: Görev ekleme, tamamlama ve silme
- **Responsive Layout**: 4 eşit alan şeklinde kategoriler
- **Floating Action Button**: Alt kısımda sabit + ikonu ile görev ekleme

## Teknik Detaylar

- **Framework**: Jetpack Compose
- **Mimari**: MVVM (Model-View-ViewModel)
- **Dil**: Kotlin
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Kurulum

1. Android Studio'yu açın
2. Projeyi import edin
3. Gradle sync işlemini tamamlayın
4. Uygulamayı çalıştırın

### Java Sürümü Sorunu

Eğer "No Java compiler found" hatası alırsanız:

1. Android Studio'da **File > Project Structure** açın
2. **SDK Location** sekmesine gidin
3. **JDK Location** için Android Studio'nun embedded JDK'sını seçin (genellikle `/Applications/Android Studio.app/Contents/jbr/Contents/Home`)
4. **Apply** ve **OK** butonlarına tıklayın
5. Projeyi yeniden sync edin

Alternatif olarak, sisteminizde Java 11 veya üzeri yüklüyse, `JAVA_HOME` environment variable'ını ayarlayabilirsiniz.

## Kullanım

1. Ana ekranda 4 kategori kartını göreceksiniz
2. Alt kısımdaki + butonuna tıklayarak yeni görev ekleyin
3. Görevleri kategorilere göre organize edin
4. Görevleri tamamlamak için checkbox'a tıklayın
5. Görevleri silmek için X butonuna tıklayın

## Tasarım Prensipleri

- **Minimalizm**: Gereksiz süslemeler yok
- **Fonksiyonellik**: Sadece gerekli özellikler
- **Okunabilirlik**: Modern tipografi
- **Kontrast**: Siyah-beyaz renk paleti
- **Temizlik**: Düzenli ve organize arayüz

## Geliştirici Notları

Uygulama Jetpack Compose ile geliştirilmiştir ve modern Android geliştirme pratiklerini takip eder. Kod yapısı:

- `model/`: Veri modelleri (Task, TaskCategory)
- `viewmodel/`: İş mantığı (TaskViewModel)
- `ui/components/`: UI bileşenleri (CategoryCard, AddTaskDialog)
- `ui/theme/`: Tema ve renkler
