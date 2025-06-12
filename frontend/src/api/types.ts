

export interface ICommonPage<T> {
  [x: string]: any;
  pageSize: number;
  totalPage: number;
  totalRow: number;
  pageNumber: number;
  records: T[];
}

export interface IUserItem{
  id: string;
  username: string;
  nickname: string;
  email: string;
  password: string;
  enabled: boolean;
  lastMineCode: string; // 当前项目ID
}

export interface ITableQueryParams{
  // 当前页
  page?: number;
  // 每页条数
  pageSize?: number;
  // 排序仅针对单个字段
  sort?: object;
  // 排序仅针对单个字段
  sortString?: string;
  // 表头筛选
  filter?: object;
  // 查询条件
  keyword?: string;
  [key: string]: any;
}