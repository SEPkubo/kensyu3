package com.example.demo.dto;
import java.io.Serializable;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ManagementUpdateRequest extends ManagementRequest implements Serializable {
  /**
   * ユーザーID
   */
  @NotNull
  private Long id;
}