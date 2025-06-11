import type { ILocal } from "../types/storage";

interface StorageData<T> {
  value: T;
  expire: number | null;
}
const createLocalStorage = <T extends ILocal>() => {
  const set = <K extends keyof T>(
    key: K,
    value: T[K],
    expire: number = 60 * 60 * 24 * 7
  ) => {
    const storageData: StorageData<T[K]> = {
      value,
      expire: new Date().getTime() + expire * 1000,
    };
    const json = JSON.stringify(storageData);
    localStorage.setItem(`${String(key)}`, json);
  };
  const clear = localStorage.clear;
  const remove = (key: keyof T) => {
    localStorage.removeItem(`${String(key)}`);
  };
  const get = <K extends keyof T>(key: K) => {
    const json = localStorage.getItem(`${String(key)}`);
    if (!json) return null;

    const storageData: StorageData<T[K]> | null = JSON.parse(json);

    if (storageData) {
      const { value, expire } = storageData;
      if (expire === null || expire >= Date.now()) return value;
    }
    remove(key);
    return null;
  };
  return {
    set,
    get,
    remove,
    clear,
  };
};
export const local = createLocalStorage();
