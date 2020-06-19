package swu.xl.mydrawingboard.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import swu.xl.mydrawingboard.Constant.Constant;

public class ExternalStorageUtil {

    /**
     * 判断是否有外部存储
     * @return
     */
    public static boolean isExternalStorageMounted() {
        // return Environment.getExternalStorageState().equals("mounted");
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取外部存储的根目录
     * @return
     */
    public static String getExternalStorageBaseDir() {
        if (isExternalStorageMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取外部存储的完整空间大小
     * 单位：MB
     * @return
     */
    public static long getExternalStorageSize() {
        if (isExternalStorageMounted()) {
            StatFs fs = new StatFs(getExternalStorageBaseDir());
            long count = fs.getBlockCountLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 获取外部存储的剩余空间大小
     * 单位：MB
     * @return
     */
    public static long getExternalStorageFreeSize() {
        if (isExternalStorageMounted()) {
            StatFs fs = new StatFs(getExternalStorageBaseDir());
            long count = fs.getFreeBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 获取外部存储的可用空间大小
     * 单位：MB
     * @return
     */
    public static long getExternalStorageAvailableSize() {
        if (isExternalStorageMounted()) {
            StatFs fs = new StatFs(getExternalStorageBaseDir());
            long count = fs.getAvailableBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 保存信息至外部存储的九大公共目录中
     * @param data 信息
     * @param type 公共目录的类型
     * @param fileName 文件名
     * @return
     */
    public static boolean saveFileToExternalStoragePublicDir(byte[] data, String type, String fileName) {
        BufferedOutputStream bos = null;
        if (isExternalStorageMounted()) {
            File file = Environment.getExternalStoragePublicDirectory(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 保存信息至外部存储根目录下的自定义目录中
     * @param data 信息
     * @param dir  自定义目录名
     * @param fileName 文件名
     * @return
     */
    public static boolean saveFileToExternalStorageCustomDir(byte[] data, String dir, String fileName) {
        BufferedOutputStream bos = null;
        if (isExternalStorageMounted()) {
            File file = new File(getExternalStorageBaseDir() + File.separator + dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 保存信息至外部存储的私有目录中
     * @param data 信息
     * @param type 目录类型
     * @param fileName 文件名
     * @param context  上下文
     * @return
     */
    public static boolean saveFileToExternalStoragePrivateFilesDir(byte[] data, String type, String fileName, Context context) {
        BufferedOutputStream bos = null;
        if (isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 保存信息至外部存储的缓存目录中
     * @param data
     * @param fileName
     * @param context
     * @return
     */
    public static boolean saveFileToExternalStoragePrivateCacheDir(byte[] data, String fileName, Context context) {
        BufferedOutputStream bos = null;
        if (isExternalStorageMounted()) {
            File file = context.getExternalCacheDir();
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 获取外部存储中指定位置的文件
     * @param fileDir 指定位置
     * @return
     */
    public static byte[] loadFileFromExternalStorage(String fileDir) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bis = new BufferedInputStream(new FileInputStream(new File(fileDir)));
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 保存bitmap图片到外部存储的私有Files目录
     * @param bitmap
     * @param fileName
     * @param context
     * @return
     */
    public static boolean saveBitmapToExternalStoragePrivateCacheDir(Bitmap bitmap, String fileName, Context context) {
        if (isExternalStorageMounted()) {
            BufferedOutputStream bos = null;
            // 获取私有的Cache缓存目录
            File file = context.getExternalCacheDir();

            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                if (fileName != null && (fileName.contains(".png") || fileName.contains(".PNG"))) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存bitmap图片到外部存储的私有Cache目录
     * @param bitmap
     * @param fileName
     * @param context
     * @return
     */
    public static String saveBitmapToExternalStoragePrivateFilesDir(Bitmap bitmap, String fileName, Context context) {
        if (isExternalStorageMounted()) {
            BufferedOutputStream bos = null;
            // 获取私有的目录
            File file = context.getExternalFilesDir(null);

            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                if (fileName != null && (fileName.contains(".png") || fileName.contains(".PNG"))) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.d(Constant.TAG,new File(file, fileName).getAbsolutePath());
            return new File(file, fileName).getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 读取外部存储中指定位置的Bitmap
     * @param filePath
     * @return
     */
    public static Bitmap loadBitmapFromExternalStorage(String filePath) {
        byte[] data = loadFileFromExternalStorage(filePath);
        if (data != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bm != null) {
                return bm;
            }
        }
        return null;
    }

    /**
     * 获取外部存储公有目录的路径
     * @param type
     * @return
     */
    public static String getExternalStoragePublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).toString();
    }

    /**
     * 获取外部存储私有Files目录的路径
     * @param context
     * @param type
     * @return
     */
    public static String getExternalStoragePrivateFilesDir(Context context, String type) {
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    /**
     * 获取外部存储私有Cache目录的路径
     * @param context
     * @return
     */
    public static String getExternalStoragePrivateCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 判断外部存储中指定位置的文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    /**
     * 删除外部存储中指定位置的文件
     * @param filePath
     * @return
     */
    public static boolean removeFileFromExternalStorage(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
