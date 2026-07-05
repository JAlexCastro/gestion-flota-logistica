import { useState } from "react";

import "./RevisionesPage.css";

import ListadoDocumentacion from "./ListadoDocumentacion";
import RegistrarDocumentacion from "./RegistrarDocumentacion";
import ActualizarDocumentacion from "./ActualizarDocumentacion";

function RevisionesPage() {

    const [vista, setVista] = useState("listar");

    return (

        <div className="revisiones-container">

            <h1 className="titulo-pagina">
                Documentación Vehículos
            </h1>

            <div className="toolbar-documentacion">

                <button
                    className={vista === "listar" ? "toolbar-btn active" : "toolbar-btn"}
                    onClick={() => setVista("listar")}
                >
                    Listar
                </button>

                <button
                    className={vista === "registrar" ? "toolbar-btn active" : "toolbar-btn"}
                    onClick={() => setVista("registrar")}
                >
                    Registrar
                </button>

                <button
                    className={vista === "actualizar" ? "toolbar-btn active" : "toolbar-btn"}
                    onClick={() => setVista("actualizar")}
                >
                    Actualizar
                </button>

            </div>

            {
                vista === "listar" &&
                <ListadoDocumentacion />
            }

            {
                vista === "registrar" &&
                <RegistrarDocumentacion />
            }

            {
                vista === "actualizar" && (
                    <ActualizarDocumentacion />
                )
            }

        </div>

    );

}

export default RevisionesPage;