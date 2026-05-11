import { getLocalProductImageUrl, getUploadDriver, type UploadDriver } from '../config/upload';

export type StoredProductImage = {
  imageUrl: string;
  imageStorage: UploadDriver;
};

export async function storeProductImage(file: Express.Multer.File): Promise<StoredProductImage> {
  return {
    imageUrl: getLocalProductImageUrl(file.filename),
    imageStorage: getUploadDriver(),
  };
}
