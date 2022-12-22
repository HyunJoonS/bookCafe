import React from "react";
import FadeLoader from "react-spinners/FadeLoader";

function Loading() {
  return (
    <div class="contentWrap">
      <div
        style={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
        }}
      >
        <FadeLoader
          color="#C63DEE"
          height={15}
          width={5}
          radius={2}
          margin={2}
        />
      </div>
    </div>
  );
}

export default Loading;